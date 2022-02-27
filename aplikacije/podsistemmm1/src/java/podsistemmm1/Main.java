/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistemmm1;

import entiteti.*;
import java.util.ArrayList;
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
import javax.persistence.TypedQuery;

/**
 *
 * @author xxx
 */
public class Main {

    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("podsistemmm1PU");
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
    
   //PODSISTEM1
    //kreiranje mesta
    public static void kreiranjeMesta(String naziv,int posBr){
       
        
        em.getTransaction().begin();
        
        Mesto m=new Mesto();
        m.setNaziv(naziv);
        m.setPosBr(posBr);
        em.persist(m);
        
        em.getTransaction().commit();
        
      
        //mozda treba poslati podsistemu 2 o tome posle razmisli!
    
    } 
    
    //dohvatanje svih mesta
     public static  String dohvMesta(){
           
           List<Mesto> mesta=em.createNamedQuery("Mesto.findAll").getResultList();
           String m=new String();
           for (Mesto mesto : mesta) {
               m+=mesto.getIdM()+"\t"+mesto.getNaziv()+"\t"+mesto.getPosBr();
               m+=",";
           }
           return m;
       }
     
    public static void kreiranjeFilijale(String naziv,String adresa,int idM){
       
        Mesto mesto=em.find(Mesto.class, idM);
        if(mesto==null){
            //ne postoji zadato mesto za koje se zeli kreiranje filijale
            return;
        }
        
        em.getTransaction().begin();
        Filijala f=new Filijala();
        f.setNaziv(naziv);
        f.setAdresa(adresa);
        
    
        f.setIdM(mesto);
        em.persist(f);
        
        em.getTransaction().commit();
        
      
        //mozda treba poslati podsistemu 2 o tome posle razmisli!
    
    } 
    

//kreiranje komitenta
      public static void kreiranjeKomitenta(String naziv,String adresa,int idM){
       
        Mesto mesto=em.find(Mesto.class, idM);
        if(mesto==null){
            //ne postoji zadato mesto za koje se zeli kreiranje filijale
            return;
        }
        
        em.getTransaction().begin();
        Komitent k=new Komitent();
        k.setNaziv(naziv);
        k.setAdresa(adresa);
        
    
        k.setIdM(mesto);
        em.persist(k);
        
        em.getTransaction().commit();
        
      
        //mozda treba poslati podsistemu 2 o tome posle razmisli!
        
        //nakon sto kreiramo komitenta u podsistemu1 kreirace se i u podsistemu2!!!
        String param=k.getIdK()+","+naziv+","+adresa+","+idM;
        ObjectMessage om=dohvKontekst().createObjectMessage(param);
        try {
            //slanje komitenta je br 17!!!
            om.setIntProperty("br", 17);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        dohvProducera().send(zahetviTema, om);
    
    }
     //dohvatanje svih filijala
     public static  String dohvFilijala(){
           
           List<Filijala> filijale=em.createNamedQuery("Filijala.findAll").getResultList();
           String f=new String();
           for (Filijala filijala : filijale) {
               f+=filijala.getIdF()+"\t"+filijala.getNaziv()+"\t"+filijala.getAdresa()+"\t\t"+filijala.getIdM().getIdM();
               f+=",";
           }
           return f;
       }
     
     //dohvatanje svih komitenata
     public static  String dohvKomitenata(){
           
           List<Komitent> komitenti=em.createNamedQuery("Komitent.findAll").getResultList();
           String k=new String();
           for (Komitent komitent: komitenti) {
               k+=komitent.getIdK()+"\t"+komitent.getNaziv()+"\t"+komitent.getAdresa()+"\t"+komitent.getIdM().getIdM();
               k+=",";
           }
           return k;
       }
   
    //promena sedista za komitenta
    public static void promenaSedistaZaKomitenta(String adresa,int idK,int idM){
       
        Komitent k=em.find(Komitent.class, idK);
      
        if(k==null){
            //ne postoji zadati komitent kome se menja sediste
            return;
        }
        
        Mesto m=em.find(Mesto.class, idM);
          if(m==null){
            //ne postoji zadato mesto 
            return;
        }
        
        em.getTransaction().begin();
        k.setAdresa(adresa);
        k.setIdM(m);
    
        em.persist(k);
        
        em.getTransaction().commit();
        
      
        //mozda treba poslati podsistemu 2 o tome posle razmisli!
        
          //nakon sto kreiramo komitenta u podsistemu1 kreirace se i u podsistemu2!!!
        String param=k.getIdK()+","+adresa+","+idM;
        ObjectMessage om=dohvKontekst().createObjectMessage(param);
        try {
            //slanje komitenta je br 17!!!
            om.setIntProperty("br", 18);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        dohvProducera().send(zahetviTema, om);
    
    }
     
    public static void proveraDaLiPostojiMesto(int idM){
        
        try {
            Mesto m=em.find(Mesto.class, idM);
            boolean postoji;
            if(m==null)postoji=false;
            else postoji=true;
            
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 19);
            om.setBooleanProperty("postoji", postoji);
            
            dohvProducera().send(odgovoriTema, om);
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    public static void proveraDaLiPostojiFilijala(int idF){
        
        try {
          Filijala f=em.find(Filijala.class, idF);
            boolean postoji;
            if(f==null)postoji=false;
            else postoji=true;
            
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 20);
            om.setBooleanProperty("postoji", postoji);
            
            dohvProducera().send(odgovoriTema, om);
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    
      //slanje baza podsistemu3!!!
     public static  void slanjeBazaPodsistemu3(){
           
        try {
            
           //slanje mesta podsistemu 3
           List<Mesto> mesta=em.createNamedQuery("Mesto.findAll").getResultList();
           String m=new String();
           for (Mesto mesto : mesta) {
               m+=mesto.getIdM()+","+mesto.getNaziv()+","+mesto.getPosBr();
               m+="#";
           }
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 22); //za mesta
            om.setObject(m);
            dohvProducera().send(odgovoriTema, om);
            System.out.println("poslao mesto");
            
           //slanje filijala podsistemu 3
           List<Filijala> filijale=em.createNamedQuery("Filijala.findAll").getResultList();
           String f=new String();
           for (Filijala filijala : filijale) {
               f+=filijala.getIdF()+","+filijala.getNaziv()+","+filijala.getAdresa()+","+filijala.getIdM().getIdM();
               f+="#";
           }
            ObjectMessage om2=dohvKontekst().createObjectMessage();
            om2.setIntProperty("br", 23);  //za filijale
            om2.setObject(f);
            
             dohvProducera().send(odgovoriTema, om2);
              System.out.println("poslao filijalu");
           //slanje komitenata podsistemu 3
           List<Komitent> komitenti=em.createNamedQuery("Komitent.findAll").getResultList();
           String k=new String();
           for (Komitent komitent : komitenti) {
               k+=komitent.getIdK()+","+komitent.getNaziv()+","+komitent.getAdresa()+","+komitent.getIdM().getIdM();
               k+="#";
           }
            ObjectMessage om3=dohvKontekst().createObjectMessage();
            om3.setIntProperty("br", 24);  //za komitente
            om3.setObject(k);
            dohvProducera().send(odgovoriTema, om3);
            
              System.out.println("poslao komitenta");
            
            
            
            
            
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
           
       }
    
     
        //slanje baza podsistemu3!!!
     public static  void slanjeBazaZaUpit16(){
           
        try {
            
           //slanje mesta podsistemu 3
           List<Mesto> mesta=em.createNamedQuery("Mesto.findAll").getResultList();
           String m=new String();
           for (Mesto mesto : mesta) {
               m+=mesto.getIdM()+","+mesto.getNaziv()+","+mesto.getPosBr();
               m+="#";
           }
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 41); //za mesta
            om.setObject(m);
            dohvProducera().send(odgovoriTema, om);
            System.out.println("poslao mesto");
            
           //slanje filijala podsistemu 3
           List<Filijala> filijale=em.createNamedQuery("Filijala.findAll").getResultList();
           String f=new String();
           for (Filijala filijala : filijale) {
               f+=filijala.getIdF()+","+filijala.getNaziv()+","+filijala.getAdresa()+","+filijala.getIdM().getIdM();
               f+="#";
           }
            ObjectMessage om2=dohvKontekst().createObjectMessage();
            om2.setIntProperty("br", 42);  //za filijale
            om2.setObject(f);
            
             dohvProducera().send(odgovoriTema, om2);
              System.out.println("poslao filijalu");
           //slanje komitenata podsistemu 3
           List<Komitent> komitenti=em.createNamedQuery("Komitent.findAll").getResultList();
           String k=new String();
           for (Komitent komitent : komitenti) {
               k+=komitent.getIdK()+","+komitent.getNaziv()+","+komitent.getAdresa()+","+komitent.getIdM().getIdM();
               k+="#";
           }
            ObjectMessage om3=dohvKontekst().createObjectMessage();
            om3.setIntProperty("br", 43);  //za komitente
            om3.setObject(k);
            dohvProducera().send(odgovoriTema, om3);
            
              System.out.println("poslao komitenta");
            
            
            
            
            
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
           
       }
    
     
     
     
     public static void main(String[] args) {
        
        try {
            JMSContext c=dohvKontekst();
            JMSConsumer consumer=c.createConsumer(zahetviTema, "br=1 OR br=10 OR br=11 OR br=12 OR br=2 OR br=3 OR br=4 OR br=19 OR br=20 OR br=21 OR br=40");
             
          while(true){
              
              
            ObjectMessage om=(ObjectMessage) consumer.receive();
            int brojZahetva=om.getIntProperty("br");
            
            switch(brojZahetva){
                case 1:
                    
                     String s=(String) om.getObject();
                     String[] parametri=s.split(",");
                      String naziv=parametri[0];
                      int posBr=Integer.parseInt(parametri[1]);
                     // int posBr=1234;
                     
                     // int posBr=4444;
                      
                      kreiranjeMesta(naziv,posBr);
                    break;
                case 2:
                          
                     String s2=(String) om.getObject();
                     String[] parametri2=s2.split(",");
                     String naziv2=parametri2[0];
                     String adresa2=parametri2[1];
                     int idM=Integer.parseInt(parametri2[2]);
                    
                      kreiranjeFilijale(naziv2,adresa2,idM);
                      
                      
                    break;
                case 3:
                       String s3=(String) om.getObject();
                     String[] parametri3=s3.split(",");
                     String naziv3=parametri3[0];
                     String adresa3=parametri3[1];
                     int idM3=Integer.parseInt(parametri3[2]);
                    
                      kreiranjeKomitenta(naziv3,adresa3,idM3);
                      
                      
                    break;
                    
                    
                case 4:
                     String s4=(String) om.getObject();
                     String[] parametri4=s4.split(",");
                     int idK4=Integer.parseInt(parametri4[0]);
                     String adresa4=parametri4[1];
                     int idM4=Integer.parseInt(parametri4[2]);
                    
                     promenaSedistaZaKomitenta(adresa4,idK4,idM4);
                    break;
                    
                case 10:
                    
                    String s10=dohvMesta();
                    ObjectMessage om10=dohvKontekst().createObjectMessage(s10);
                    om10.setIntProperty("br",10);
                    dohvProducera().send(odgovoriTema, om10);
                    
                    
                    break;
                    
                case 11:
                     String s11=dohvFilijala();
                    ObjectMessage om11=dohvKontekst().createObjectMessage(s11);
                    om11.setIntProperty("br",11);
                    dohvProducera().send(odgovoriTema, om11);
                    break;
                    
                    
                 case 12:
                    String s12=dohvKomitenata();
                    ObjectMessage om12=dohvKontekst().createObjectMessage(s12);
                    om12.setIntProperty("br",12);
                    dohvProducera().send(odgovoriTema, om12);
                    break;
            
                 //provera da li postoji zadato mesto
                case 19:
                    String s19=(String) om.getObject();
                    int idM19=Integer.parseInt(s19);
                    proveraDaLiPostojiMesto(idM19);
                    break;
                 //provera da li postoji zadata filjala
                case 20:
                    String s20=(String) om.getObject();
                    int idF20=Integer.parseInt(s20);
                    proveraDaLiPostojiFilijala(idF20);
                    break;
                    
                case 21:
                    slanjeBazaPodsistemu3();
                    break;
                    
               case 40:
                    slanjeBazaZaUpit16();
                    break;
            }
       }
          
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
