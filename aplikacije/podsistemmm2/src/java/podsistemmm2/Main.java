/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistemmm2;

import entiteti.Isplata;
import entiteti.Komitent;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Transakcija;
import entiteti.Uplata;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author xxx
 */
public class Main {

   
    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("podsistemmm2PU");
    private static EntityManager em=emf.createEntityManager();
    
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory cf;
    
      
    @Resource(lookup="zahtev")
    public static Topic zahetviTema;
    
    @Resource(lookup="odgovor")
    public static Topic odgovoriTema;
    
    private static JMSContext context;
    private static JMSProducer producer;
    //fali producer!
    
    private static JMSContext dohvKontekst(){
        if(context!=null)return context;
        else return cf.createContext();
        
    }
    
  private static JMSProducer dohvProducera(){
        if(context==null) context=cf.createContext();
        if(producer!=null)return producer;
        else {
            producer=context.createProducer();
            return producer;
        }
    }
   
     //otvaranje racuna
    public static void otvaranjeRacuna(int idK,int idM,int dozMinus){
        
        Komitent k=em.find(Komitent.class, idK);
        if(k==null)return;  //nema komitenta
        
        em.getTransaction().begin();
        
        
        Racun r=new Racun();
        r.setBrTransakcija(0);
        r.setDozvoljeniMinus(dozMinus);
        r.setIdK(k);
        r.setIdM(idM);
        r.setStanje(0);
        r.setStatus("A");
        r.setDatumIVremeOtvaranja(new Date(System.currentTimeMillis()));
        
       
        em.persist(r);
        
        em.getTransaction().commit();
        
      
        //mozda treba poslati podsistemu 2 o tome posle razmisli!
        //ovde msm da ne treba slati nikom nista
    
    } 
    
    //dodavanje komitenta
    public static void dodavanjeKomitenta(int idK,String naziv,String adresa,int idM){
        
       
        em.getTransaction().begin();
        
        Komitent k=new Komitent(idK, naziv, adresa, idM);
      
       
        em.persist(k);
        
        em.getTransaction().commit();
        
    } 
   
     //promena sedista za komitenta -- > update
    public static void promenaSedistaKomitenta(int idK,String adresa,int idM){
        //vec je provereno da postoje mesto i komitent
        Komitent k=em.find(Komitent.class, idK);
        k.setAdresa(adresa);
        k.setMesto(idM);
        em.getTransaction().begin();
        
       
        em.persist(k);
        
        em.getTransaction().commit();
        
    } 
   
    public static void zatvaranjeRacuna(int idR){
    
        Racun r=em.find(Racun.class, idR);
        if(r==null)return; //ne postoji zadati racun pa se ne moze ni zatvoriti
        
        r.setStatus("Z");
        
        em.getTransaction().begin();
        
       
        em.persist(r);
        
        em.getTransaction().commit();
    
    
    }
    
    
    public static  String dohvatanjeRacunaZakomitenta(int idK){
        
        Komitent k=em.find(Komitent.class, idK);
        if(k==null)return null;
           
           List<Racun> racuni=em.createQuery("select r from Racun r where r.idK=:idK",Racun.class).setParameter("idK",k).getResultList();
           String r=new String();
           for (Racun racun: racuni) {
               r+=racun.getIdR()+"\t"+racun.getIdM()+"\t"+racun.getStanje()+"\t"+racun.getIdK().getIdK()+"\t"+racun.getDozvoljeniMinus()+"\t"+racun.getStatus()+"\t"+racun.getDatumIVremeOtvaranja()+"\t\t"+racun.getBrTransakcija();
               r+=",";
           }
           return r;
       }
    
    
       //kreiranje uplate
    public static void kreiranjeUplate(int iznos,int idR,String svrha,int idF){
        
        Racun r=em.find(Racun.class, idR);
        if(r==null)return;  //nema racuna na koji se vrsi uplata
        
        if(r.getStatus().equals("Z"))return; //ne moze uplata na zatvoren racun
        
        
        em.getTransaction().begin();
        
        //kreiranje transakcije
        Transakcija t=new Transakcija();
        t.setIznos(iznos);
        t.setDatumIVreme(new Date(System.currentTimeMillis()));
        t.setSvrha(svrha);
        t.setRedniBr(r.getBrTransakcija()+1);
        t.setIdRac(r);
      
         em.persist(t);
         em.getTransaction().commit();
         
         
         
         
        //azuriranje racuna
        r.setStanje(r.getStanje()+iznos);
        r.setBrTransakcija(r.getBrTransakcija()+1);
        if(-r.getStanje()<r.getDozvoljeniMinus())r.setStatus("A");
        
        //kreiranje uplate za racun
        Uplata u=new Uplata();
        u.setIdT(t.getIdT());
        u.setIdFil(idF);
        
        //dodala
      //  u.setTransakcija(t);
      //  t.setUplata(u);
       
       
        em.getTransaction().begin();
        
        em.persist(r);
        em.persist(u);
          
        em.getTransaction().commit();
        
      
    
    } 
       //kreiranje isplate
    public static void kreiranjeIsplate(int iznos,int idR,String svrha,int idF){
        
        Racun r=em.find(Racun.class, idR);
        if(r==null)return;  //nema racuna sa kojeg se vrsi isplata
        
        if(r.getStatus().equals("Z") || r.getStatus().equals("B"))return; //ne moze isplaata sa zatvorenog ili blokiranog racuna
        
        
        em.getTransaction().begin();
        
        //kreiranje transakcije
        Transakcija t=new Transakcija();
        t.setIznos(iznos);
        t.setDatumIVreme(new Date(System.currentTimeMillis()));
        t.setSvrha(svrha);
        t.setRedniBr(r.getBrTransakcija()+1);
        t.setIdRac(r);
      
         em.persist(t);
         em.getTransaction().commit();
         
        //azuriranje racuna
        r.setStanje(r.getStanje()-iznos);
        r.setBrTransakcija(r.getBrTransakcija()+1);
        if(-r.getStanje()>r.getDozvoljeniMinus())r.setStatus("B");
        
        //kreiranje uplate za racun
        Isplata i=new Isplata();
        i.setIdT(t.getIdT());
        i.setIdFil(idF);
        
        //dodala
      //  u.setTransakcija(t);
      //  t.setUplata(u);
       
       
        em.getTransaction().begin();
        
        em.persist(r);
        em.persist(i);
          
        em.getTransaction().commit();
        
      
    
    } 
  
         //kreiranje prenosa sa racuna na racun
    public static void kreiranjePrenosa(int iznos,int idRSa,int idRNa,String svrha){
        
        Racun rSa=em.find(Racun.class, idRSa);
        Racun rNa=em.find(Racun.class, idRNa);
        
        if(rNa==null || rSa==null)return;  //nema racuna na koji se vrsi uplata ili sa kog se vrsi isplata
        
        if(rSa.getStatus().equals("Z") || rSa.getStatus().equals("B") || rNa.getStatus().equals("Z"))return; 
        
        
    
        
        //kreiranje transakcije
        Transakcija t1=new Transakcija();
        t1.setIznos(iznos);
        t1.setDatumIVreme(new Date(System.currentTimeMillis()));
        t1.setSvrha(svrha);
        t1.setRedniBr(rSa.getBrTransakcija()+1);
        t1.setIdRac(rSa);
      
        Transakcija t2=new Transakcija();
        t2.setIznos(iznos);
        t2.setDatumIVreme(new Date(System.currentTimeMillis()));
        t2.setSvrha(svrha);
        t2.setRedniBr(rNa.getBrTransakcija()+1);
        t2.setIdRac(rNa);
         em.getTransaction().begin();
         em.persist(t1);
         em.getTransaction().commit();
          
         em.getTransaction().begin();
         em.persist(t2);
         em.getTransaction().commit();
         
         
         
         
        //azuriranje racunaSa
        rSa.setStanje(rSa.getStanje()-iznos);
        rSa.setBrTransakcija(rSa.getBrTransakcija()+1);
        if(-rSa.getStanje()>rSa.getDozvoljeniMinus())rSa.setStatus("B");
        
        //azuriranje racunaNa
        rNa.setStanje(rNa.getStanje()+iznos);
        rNa.setBrTransakcija(rNa.getBrTransakcija()+1);
        if(-rNa.getStanje()<rNa.getDozvoljeniMinus())rNa.setStatus("A");
        
        //kreiranje prenosa
        Prenos p1=new Prenos();
        p1.setIdT(t1.getIdT());
        p1.setIdRacSa(rSa);
        p1.setIdRacKa(rNa);
        
      
        Prenos p2=new Prenos();
        p2.setIdT(t2.getIdT());
        p2.setIdRacSa(rSa);
        p2.setIdRacKa(rNa);
       
       
        em.getTransaction().begin();
        
        em.persist(rNa);
        em.persist(rSa);
        em.persist(p1);
        em.persist(p2);
          
        em.getTransaction().commit();
        
      
    
    } 
 
    
    
    public static  String dohvatanjeTransakcijaZaRacun(int idR){
        
        Racun r=em.find(Racun.class, idR);
        if(r==null)return null;
           
           List<Transakcija> transakcije=em.createQuery("select t from Transakcija t where t.idRac=:idR",Transakcija.class).setParameter("idR",r).getResultList();
           String t=new String();
           for (Transakcija transakcija: transakcije) {
               t+=transakcija.getIdT()+"\t"+transakcija.getIznos()+"\t"+transakcija.getDatumIVreme()+"\t"+transakcija.getSvrha()+"\t"+transakcija.getRedniBr()+"\t"+transakcija.getIdRac().getIdR();
               t+=",";
           }
           return t;
       }
    
    
     public static void  slanjeBazaPodsistemu3(){
           
        try {
            
           //slanje racuna podsistemu 3
           List<Racun> racuni=em.createNamedQuery("Racun.findAll").getResultList();
           String r=new String();
           for (Racun racun : racuni) {
               r+=racun.getIdR()+","+racun.getIdM()+","+racun.getStanje()+","+racun.getIdK().getIdK()+","+racun.getDozvoljeniMinus()+","+racun.getStatus()+","+racun.getDatumIVremeOtvaranja()+","+racun.getBrTransakcija();
               r+="#";
           }
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 26); //za racune
            om.setObject(r);
            dohvProducera().send(odgovoriTema, om);
              System.out.println("poslao racun");
            
            
            
           //slanje transakcija podsistemu 3
           List<Transakcija> transakcije=em.createNamedQuery("Transakcija.findAll").getResultList();
           String t=new String();
           for (Transakcija transakcija : transakcije) {
               t+=transakcija.getIdT()+","+transakcija.getIznos()+","+transakcija.getDatumIVreme()+","+transakcija.getSvrha()+","+transakcija.getRedniBr()+","+transakcija.getIdRac().getIdR();
               t+="#";
           }
            ObjectMessage om2=dohvKontekst().createObjectMessage();
            om2.setIntProperty("br", 27);  //za transakcije
            om2.setObject(t);
            dohvProducera().send(odgovoriTema, om2);
                 System.out.println("poslao transakciju");
            
            
           //slanje uplata podsistemu 3
           List<Uplata> uplate=em.createNamedQuery("Uplata.findAll").getResultList();
           String u=new String();
           for (Uplata uplata : uplate) {
               u+=uplata.getIdT()+","+uplata.getIdFil();
               u+="#";
           }
            ObjectMessage om3=dohvKontekst().createObjectMessage();
            om3.setIntProperty("br", 28);  //za uplatu
            om3.setObject(u);
            dohvProducera().send(odgovoriTema, om3);
                 System.out.println("poslato uplatu");
            
               
           //slanje isplata podsistemu 3
           List<Isplata> isplate=em.createNamedQuery("Isplata.findAll").getResultList();
           String i=new String();
           for (Isplata isplata : isplate) {
               i+=isplata.getIdT()+","+isplata.getIdFil();
               i+="#";
           }
            ObjectMessage om4=dohvKontekst().createObjectMessage();
            om4.setIntProperty("br", 29);  //za isplatu
            om4.setObject(i);
            dohvProducera().send(odgovoriTema, om4);
                 System.out.println("poslao isplatu");
            
            
            
         //   System.out.println("pre svega");
            //slanje prenosa podsistemu 3
           List<Prenos> prenosi=em.createNamedQuery("Prenos.findAll").getResultList();
           String p=new String();
           for (Prenos prenos :prenosi) {
               p+=prenos.getIdT()+","+prenos.getIdRacSa().getIdR()+","+prenos.getIdRacKa().getIdR();
               p+="#";
           }
            ObjectMessage om5=dohvKontekst().createObjectMessage();
            om5.setIntProperty("br", 30);  //za prneos
            om5.setObject(p);
           //   System.out.println("pre poslao prenos prenos");
            dohvProducera().send(odgovoriTema, om5);
            
            System.out.println("poslao prenos");
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
           
       }
     
   
        
     public static void  slanjeBazaZaUpit16(){
           
        try {
            
           //slanje racuna podsistemu 3
           List<Racun> racuni=em.createNamedQuery("Racun.findAll").getResultList();
           String r=new String();
           for (Racun racun : racuni) {
               r+=racun.getIdR()+","+racun.getIdM()+","+racun.getStanje()+","+racun.getIdK().getIdK()+","+racun.getDozvoljeniMinus()+","+racun.getStatus()+","+racun.getDatumIVremeOtvaranja()+","+racun.getBrTransakcija();
               r+="#";
           }
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 45); //za racune
            om.setObject(r);
            dohvProducera().send(odgovoriTema, om);
              System.out.println("poslao racun");
            
            
            
           //slanje transakcija podsistemu 3
           List<Transakcija> transakcije=em.createNamedQuery("Transakcija.findAll").getResultList();
           String t=new String();
           for (Transakcija transakcija : transakcije) {
               t+=transakcija.getIdT()+","+transakcija.getIznos()+","+transakcija.getDatumIVreme()+","+transakcija.getSvrha()+","+transakcija.getRedniBr()+","+transakcija.getIdRac().getIdR();
               t+="#";
           }
            ObjectMessage om2=dohvKontekst().createObjectMessage();
            om2.setIntProperty("br", 46);  //za transakcije
            om2.setObject(t);
            dohvProducera().send(odgovoriTema, om2);
                 System.out.println("poslao transakciju");
            
            
           //slanje uplata podsistemu 3
           List<Uplata> uplate=em.createNamedQuery("Uplata.findAll").getResultList();
           String u=new String();
           for (Uplata uplata : uplate) {
               u+=uplata.getIdT()+","+uplata.getIdFil();
               u+="#";
           }
            ObjectMessage om3=dohvKontekst().createObjectMessage();
            om3.setIntProperty("br", 47);  //za uplatu
            om3.setObject(u);
            dohvProducera().send(odgovoriTema, om3);
                 System.out.println("poslato uplatu");
            
               
           //slanje isplata podsistemu 3
           List<Isplata> isplate=em.createNamedQuery("Isplata.findAll").getResultList();
           String i=new String();
           for (Isplata isplata : isplate) {
               i+=isplata.getIdT()+","+isplata.getIdFil();
               i+="#";
           }
            ObjectMessage om4=dohvKontekst().createObjectMessage();
            om4.setIntProperty("br", 48);  //za isplatu
            om4.setObject(i);
            dohvProducera().send(odgovoriTema, om4);
                 System.out.println("poslao isplatu");
            
            
            
         //   System.out.println("pre svega");
            //slanje prenosa podsistemu 3
           List<Prenos> prenosi=em.createNamedQuery("Prenos.findAll").getResultList();
           String p=new String();
           for (Prenos prenos :prenosi) {
               p+=prenos.getIdT()+","+prenos.getIdRacSa().getIdR()+","+prenos.getIdRacKa().getIdR();
               p+="#";
           }
            ObjectMessage om5=dohvKontekst().createObjectMessage();
            om5.setIntProperty("br", 49);  //za prneos
            om5.setObject(p);
           //   System.out.println("pre poslao prenos prenos");
            dohvProducera().send(odgovoriTema, om5);
            
            System.out.println("poslao prenos");
            
           System.out.println("poslao za upit 16"); 
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
           
       }
     
     
    public static void main(String[] args) {
          try {
            JMSContext c=dohvKontekst();
            JMSConsumer consumer=c.createConsumer(zahetviTema, "br=5 OR br=17 OR br=18 OR br=6 OR br=13 OR br=8 OR br=9 OR br=14 OR br=7 OR br=25 OR br=44");
             
          while(true){
              
            ObjectMessage om=(ObjectMessage) consumer.receive();
            int brojZahetva=om.getIntProperty("br");
            
            switch(brojZahetva){
                case 5:
                     String s5=(String) om.getObject();
                     String[] parametri5=s5.split(",");
                    
                     int idK5=Integer.parseInt(parametri5[0]);
                     int idM5=Integer.parseInt(parametri5[1]);
                     int dozMinus5=Integer.parseInt(parametri5[2]);
                    
                    otvaranjeRacuna(idK5, idM5, dozMinus5);
                    
                     
                    break;
                 case 6:
                     String s6=(String) om.getObject();
                     int idR6=Integer.parseInt(s6);
                     zatvaranjeRacuna(idR6);
                   
                    break;
                 case 7:
                     String s7=(String) om.getObject();
                     String[] parametri7=s7.split(",");
                    
                     int iznos7=Integer.parseInt(parametri7[0]);
                     int idRSa7=Integer.parseInt(parametri7[1]);
                     int idRNa7=Integer.parseInt(parametri7[2]);
                     String svrha7=parametri7[3];
                     kreiranjePrenosa(iznos7,idRSa7,idRNa7,svrha7);
                     
                    break;
                 case 8:
                     String s8=(String) om.getObject();
                     String[] parametri8=s8.split(",");
                    
                     int iznos8=Integer.parseInt(parametri8[0]);
                     int idR8=Integer.parseInt(parametri8[1]);
                     String svrha8=parametri8[2];
                     int idF8=Integer.parseInt(parametri8[3]);
                    
                     kreiranjeUplate(iznos8,idR8,svrha8,idF8);
                     
                    break;
                 case 9:
                     String s9=(String) om.getObject();
                     String[] parametri9=s9.split(",");
                    
                     int iznos9=Integer.parseInt(parametri9[0]);
                     int idR9=Integer.parseInt(parametri9[1]);
                     String svrha9=parametri9[2];
                     int idF9=Integer.parseInt(parametri9[3]);
                    
                     kreiranjeIsplate(iznos9,idR9,svrha9,idF9);
                     
                    break;
                  case 13:
                    String ss13=(String) om.getObject();
                    int idK13=Integer.parseInt(ss13);
                    String s13=dohvatanjeRacunaZakomitenta(idK13);
                    ObjectMessage om13=dohvKontekst().createObjectMessage(s13);
                    om13.setIntProperty("br",13);
                    dohvProducera().send(odgovoriTema, om13);
                    break;
                    
                case 14:
                    String ss14=(String) om.getObject();
                    int idR14=Integer.parseInt(ss14);
                    String s14=dohvatanjeTransakcijaZaRacun(idR14);
                    ObjectMessage om14=dohvKontekst().createObjectMessage(s14);
                    om14.setIntProperty("br",14);
                    dohvProducera().send(odgovoriTema, om14);
                    break;    
                 case 17:  //dodavanje komitenta kreiranog u podsistemu1!!!!
                     String s17=(String) om.getObject();
                     String[] parametri17=s17.split(",");
                    
                     int idK17=Integer.parseInt(parametri17[0]);
                     String naziv17=parametri17[1];
                     String adresa17=parametri17[2];
                     int idM17=Integer.parseInt(parametri17[3]);
                    
                     dodavanjeKomitenta(idK17, naziv17, adresa17, idM17);
                     
                    break;
                case 18:  //azuriranje adrese i mesta za komitenta
                     String s18=(String) om.getObject();
                     String[] parametri18=s18.split(",");
                    
                    int idK18=Integer.parseInt(parametri18[0]);
                    String adresa18=parametri18[1];
                    int idM18=Integer.parseInt(parametri18[2]);
                    
                    promenaSedistaKomitenta(idK18, adresa18, idM18);
                     
                    break;
                    
               case 25:
                   System.out.println("slanje podsistemu3");
                    slanjeBazaPodsistemu3();
                    break;
               case 44:
                   System.out.println("upit16");
                    slanjeBazaZaUpit16();
                    break;
            
            }
       }
          
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
