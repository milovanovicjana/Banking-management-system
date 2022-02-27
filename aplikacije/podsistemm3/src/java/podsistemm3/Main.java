/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistemm3;

import entiteti.*;
import entiteti.Transakcija;
import java.util.ArrayList;
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
import javax.jms.Message;
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

    
    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("podsistemm3PU");
    private static EntityManager em=emf.createEntityManager();
    
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory cf;
    
      
    @Resource(lookup="zahtev")
    public static Topic zahetviTema;
    
    @Resource(lookup="odgovor")
    public static Topic odgovoriTema;
    
    private static JMSContext context;
    private static JMSProducer producer;
 
    
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
   
    private static void dohvatanjeBazaIzPodsistema1(){
   
   
    try {
            //prvo pravimo kominikaciju podsistema3 na svaka 2 minuta sa podsistemom 1!!!
            
            //dohvatanje iz podsistema1
            
           JMSContext c=dohvKontekst();
            JMSConsumer mestoConsumer=c.createConsumer(odgovoriTema, "br=22",false);
            JMSConsumer filijalaConsumer=c.createConsumer(odgovoriTema, "br=23",false);
            JMSConsumer komitentConsumer=c.createConsumer(odgovoriTema, "br=24",false);
            
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 21);
            
            dohvProducera().send(zahetviTema,om);
            
            
           
            
          
            //prihvatanje mesta
            ObjectMessage om2=(ObjectMessage) mestoConsumer.receive();
            String svaMesta=(String) om2.getObject();
            
            String []mesta=svaMesta.split("#");
            for (String string : mesta) {
                //System.out.println(string);
                String []mesto=string.split(",");
                Mesto m=new Mesto();
                m.setIdM(Integer.parseInt(mesto[0]));
                m.setNaziv(mesto[1]);
                m.setPosBr(Integer.parseInt(mesto[2]));
                
                em.getTransaction().begin();
                em.merge(m);
                em.getTransaction().commit();
                
            }
            System.out.println("azurirao mesto");
            //prihavtanje filijala
            
            ObjectMessage om3=(ObjectMessage) filijalaConsumer.receive();
            
            String sveFilijale=(String) om3.getObject();
            
            String []filijale=sveFilijale.split("#");
            for (String string : filijale) {
               // System.out.println(string);
                String []filijala=string.split(",");
                Filijala f=new Filijala();
                f.setIdF(Integer.parseInt(filijala[0]));
                f.setNaziv(filijala[1]);
                f.setAdresa(filijala[2]);
                Mesto me=em.find(Mesto.class,Integer.parseInt(filijala[3]));
                f.setIdM(me);
                
                em.getTransaction().begin();
                em.merge(f);
                em.getTransaction().commit();
                
            }
               System.out.println("azurirao filijalu");
             //prihavtanje komitenta
            
            ObjectMessage om4=(ObjectMessage) komitentConsumer.receive();
             
            String sviKomitenti=(String) om4.getObject();
            
            String []komitenti=sviKomitenti.split("#");
            for (String string : komitenti) {
             //   System.out.println(string);
                String []komitent=string.split(",");
                Komitent k=new Komitent();
                k.setIdK(Integer.parseInt(komitent[0]));
                k.setNaziv(komitent[1]);
                k.setAdresa(komitent[2]);
                Mesto me=em.find(Mesto.class,Integer.parseInt(komitent[3]));
                k.setIdM(me);
                
                em.getTransaction().begin();
                em.merge(k);
                em.getTransaction().commit();
                
            }
            
            System.out.println("azurirao komitneta");  
            
   
         
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   
   
   
   
   
   
   
   
   }
    
    private static void dohvatanjeBazaIzPodsistema2(){
     try {
            //prvo pravimo kominikaciju podsistema3 na svaka 2 minuta sa podsistemom 2!!!
            
            //dohvatanje iz podsistema2
            JMSContext c=dohvKontekst();
            JMSConsumer racunConsumer=c.createConsumer(odgovoriTema, "br=26",false);
            JMSConsumer transakcijaConsumer=c.createConsumer(odgovoriTema, "br=27",false);
            JMSConsumer uplataConsumer=c.createConsumer(odgovoriTema, "br=28",false);
            JMSConsumer isplataConsumer=c.createConsumer(odgovoriTema, "br=29",false);
            JMSConsumer prenosConsumer=c.createConsumer(odgovoriTema, "br=30",false);
          
          
            
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 25);
            
            dohvProducera().send(zahetviTema,om);
            
            
          
            
          
            //prihvatanje racuna
            ObjectMessage om2=(ObjectMessage) racunConsumer.receive();
            String sviRacuni=(String) om2.getObject();
            
            String []racuni=sviRacuni.split("#");
            for (String string : racuni) {
                String []racun=string.split(",");
                Racun r=new Racun();
                r.setIdR(Integer.parseInt(racun[0]));
                r.setIdM( Integer.parseInt(racun[1]));
                r.setStanje( Integer.parseInt(racun[2]));
                Komitent k=em.find(Komitent.class,  Integer.parseInt(racun[3]));
                r.setIdK(k);
                r.setDozvoljeniMinus( Integer.parseInt(racun[4]));
                r.setStatus(racun[5]);
                r.setDatumIVremeOtvaranja(new Date(System.currentTimeMillis()));
                r.setBrTransakcija( Integer.parseInt(racun[7]));
                
                em.getTransaction().begin();
                em.merge(r);
                em.getTransaction().commit();
                
            }
            
            System.out.println("azurirao racun");
            //prihavtanje transakije
            
            ObjectMessage om3=(ObjectMessage) transakcijaConsumer.receive();
            
            String svetransakcije=(String) om3.getObject();
            
            String []transakcije=svetransakcije.split("#");
            for (String string : transakcije) {
               // System.out.println(string);
                String []transakcija=string.split(",");
                Transakcija t=new Transakcija();
               t.setIdT( Integer.parseInt(transakcija[0]));
                t.setIznos( Integer.parseInt(transakcija[1]));
                t.setDatumIVreme(new Date(System.currentTimeMillis()));
                t.setSvrha(transakcija[3]);
                t.setRedniBr( Integer.parseInt(transakcija[4]));
                
                Racun ra=em.find(Racun.class,  Integer.parseInt(transakcija[5]));
                t.setIdRac(ra);
                
                em.getTransaction().begin();
                em.merge(t);
                em.getTransaction().commit();
                
            }
             System.out.println("azurirao transakciju");
             //prihavtanje uplata
            
            ObjectMessage om4=(ObjectMessage) uplataConsumer.receive();
            
            String sveUplate=(String) om4.getObject();
            
            String []uplate=sveUplate.split("#");
            for (String string : uplate) {
             //   System.out.println(string);
                String []uplata=string.split(",");
                Transakcija t=em.find(Transakcija.class,  Integer.parseInt(uplata[0]));
                Uplata u=new Uplata();
                u.setIdT(t.getIdT());
                u.setIdFil(Integer.parseInt(uplata[1]));
                
                em.getTransaction().begin();
                em.merge(u);
                em.getTransaction().commit();
                
            }
             System.out.println("azurirao uplatu");
             //prihavtanje isplate
            
            ObjectMessage om5=(ObjectMessage) isplataConsumer.receive();
            
            String sveIsplate=(String) om5.getObject();
            
            String []isplate=sveIsplate.split("#");
            for (String string : isplate) {
              //  System.out.println(string);
                String []isplata=string.split(",");
                Transakcija t=em.find(Transakcija.class,  Integer.parseInt(isplata[0]));
                Isplata i=new Isplata();
                i.setIdT(t.getIdT());
                i.setIdFil( Integer.parseInt(isplata[1]));
                
                em.getTransaction().begin();
                em.merge(i);
                em.getTransaction().commit();
                
            }
             System.out.println("azurirao isplatu");
   
                  //prihavtanje prenosa
            
      
            ObjectMessage om6=(ObjectMessage) prenosConsumer.receive();
            
             
             
            String sviPrenosi=(String) om6.getObject();
            
            String []prenosi=sviPrenosi.split("#");
            for (String string : prenosi) {
              //  System.out.println(string);
                String []prenos=string.split(",");
                Transakcija t=em.find(Transakcija.class,  Integer.parseInt(prenos[0]));
                Prenos p=new Prenos();
                p.setIdT(t.getIdT());
                
                Racun sa=em.find(Racun.class, Integer.parseInt(prenos[1]));
                p.setIdRacSa(sa);
                
                Racun na=em.find(Racun.class, Integer.parseInt(prenos[2]));
                p.setIdRacKa(na);
                
                em.getTransaction().begin();
                em.merge(p);
                em.getTransaction().commit();
                
            }
          System.out.println("azurirao prenos");
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   
   
   
   
   
   
   
   
    
    }
    
    
   //dohvatanje svih podataka iz rezervne kopije
    public static  String dohvSvihPodatakaIzRezervneKopije(){
           
           List<Mesto> mesta=em.createNamedQuery("Mesto.findAll").getResultList();
           String m=new String();
         
           m+="IdM\tNaziv\tPostanskiBroj";
           m+=",";
           for (Mesto mesto : mesta) {
               m+=mesto.getIdM()+"\t"+mesto.getNaziv()+"\t"+mesto.getPosBr();
               m+=",";
           }
           m+="#";
           
           m+="IdF\tNaziv\t\tAdresa\t\t\tIdM";
           m+=",";
           List<Filijala> filijale=em.createNamedQuery("Filijala.findAll").getResultList();
        
           for (Filijala filijala : filijale) {
               m+=filijala.getIdF()+"\t"+filijala.getNaziv()+"\t"+filijala.getAdresa()+"\t\t"+filijala.getIdM().getIdM();
               m+=",";
           }
           m+="#";
           
           m+="IdK\tNaziv\t\tAdresa\t\tIdM";
           m+=",";
           List<Komitent> komitenti=em.createNamedQuery("Komitent.findAll").getResultList();
           for (Komitent komitent: komitenti) {
               m+=komitent.getIdK()+"\t"+komitent.getNaziv()+"\t"+komitent.getAdresa()+"\t"+komitent.getIdM().getIdM();
               m+=",";
           }
           m+="#";
           
           m+="IdR\tIdM\tStanje\tIdK\tDozMin\tStatus\tDatumIVremeKreiranja\t\tBrTransakcija";
           m+=","; 
           List<Racun> racuni=em.createNamedQuery("Racun.findAll").getResultList();
           for (Racun racun: racuni) {
               m+=racun.getIdR()+"\t"+racun.getIdM()+"\t"+racun.getStanje()+"\t"+racun.getIdK().getIdK()+"\t"+racun.getDozvoljeniMinus()+"\t"+racun.getStatus()+"\t"+racun.getDatumIVremeOtvaranja()+"\t\t"+racun.getBrTransakcija();
               m+=",";
           }
           m+="#";
           
           m+="IdT\tIznos\tDatumIVreme\t\tSvrha\tRedniBr\tIdRac";
           m+=",";
           List<Transakcija> transakcije=em.createNamedQuery("Transakcija.findAll").getResultList();
           for (Transakcija transakcija: transakcije) {
               m+=transakcija.getIdT()+"\t"+transakcija.getIznos()+"\t"+transakcija.getDatumIVreme()+"\t"+transakcija.getSvrha()+"\t"+transakcija.getRedniBr()+"\t"+transakcija.getIdRac().getIdR();
               m+=",";
           }
           
           m+="#";
           
           m+="IdT\tIdFil";
           m+=",";
           List<Uplata>uplate=em.createNamedQuery("Uplata.findAll").getResultList();
           for (Uplata uplata : uplate) {
               m+=uplata.getIdT()+"\t"+uplata.getIdFil();
               m+=","; 
           }
           
           
           m+="#";
           
           m+="IdT\tIdFil";
           m+=",";
           List<Isplata>isplate=em.createNamedQuery("Isplata.findAll").getResultList();
           for (Isplata isplata : isplate) {
               m+=isplata.getIdT()+"\t"+isplata.getIdFil();
               m+=","; 
           }
           
           
            m+="#";
           
           m+="IdT\tIdRacSa\tIdRacNa";
           m+=",";
           List<Prenos>prenosi=em.createNamedQuery("Prenos.findAll").getResultList();
           for (Prenos prenos : prenosi) {
               m+=prenos.getIdT()+"\t"+prenos.getIdRacSa().getIdR()+"\t"+prenos.getIdRacKa().getIdR();
               m+=","; 
           }
           
  
           
           return m;
    }
  
    //dohvatanje razlika
   public static  String dohvRazlike(){
     
          String razl=new String();
         
          
     
        try {
            
            
            JMSContext c=dohvKontekst();
            JMSConsumer mestoConsumer=c.createConsumer(odgovoriTema, "br=41",false);
            JMSConsumer filijalaConsumer=c.createConsumer(odgovoriTema, "br=42",false);
            JMSConsumer komitentConsumer=c.createConsumer(odgovoriTema, "br=43",false);
            
            
            
            ObjectMessage om=dohvKontekst().createObjectMessage();
            om.setIntProperty("br", 40);
            
            dohvProducera().send(zahetviTema,om);
           
            //prihvatanje mesta
            ObjectMessage om2=(ObjectMessage) mestoConsumer.receive();
            String svaMesta=(String) om2.getObject();
            
            String []mesta=svaMesta.split("#");
            List<Mesto> mesta1=new ArrayList<Mesto>();
            for (String string : mesta) {
                //System.out.println(string);
                String []mesto=string.split(",");
                Mesto m=new Mesto();
                m.setIdM(Integer.parseInt(mesto[0]));
                m.setNaziv(mesto[1]);
                m.setPosBr(Integer.parseInt(mesto[2]));
                mesta1.add(m);  
            }
            
             razl+="IdM\tNaziv\tPostanskiBroj";
             razl+=",";

            List<Mesto> mesta2=em.createNamedQuery("Mesto.findAll").getResultList();
            if(mesta1.size()>mesta2.size()){
                int broj=mesta1.size()-mesta2.size();
                int index=mesta1.size()-broj;
                for(int i=0;i<broj;i++){
                         razl+=mesta1.get(index).getIdM()+"\t"+mesta1.get(index).getNaziv()+"\t"+mesta1.get(index).getPosBr();
                         index++;
                         razl+=","; 
                }
              
            }
              razl+="#";
            
            //prihavtanje filijala
              
            ObjectMessage om3=(ObjectMessage) filijalaConsumer.receive();
            
            String sveFilijale=(String) om3.getObject();
            
            String []filijale=sveFilijale.split("#");
            List<Filijala> filijale1=new ArrayList<Filijala>();
            for (String string : filijale) {
               // System.out.println(string);
                String []filijala=string.split(",");
                Filijala f=new Filijala();
                f.setIdF(Integer.parseInt(filijala[0]));
                f.setNaziv(filijala[1]);
                f.setAdresa(filijala[2]);
                Mesto me=em.find(Mesto.class,Integer.parseInt(filijala[3]));
                     //dodala
                if(me==null){
                    System.out.println("jeste null");
                    me=new Mesto();
                    me.setIdM(Integer.parseInt(filijala[3]));
                
                }
                //dodala   
                f.setIdM(me);
                
                filijale1.add(f);
                
            }
            
            
              razl+="IdF\tNaziv\t\tAdresa\t\t\tIdM";
             razl+=",";

            List<Filijala> filijale2=em.createNamedQuery("Filijala.findAll").getResultList();
            if(filijale1.size()>filijale2.size()){
                int broj=filijale1.size()-filijale2.size();
                int index=filijale1.size()-broj;
                for(int i=0;i<broj;i++){
                         razl+=filijale1.get(index).getIdF()+"\t"+filijale1.get(index).getNaziv()+"\t"+filijale1.get(index).getAdresa()+"\t"+filijale1.get(index).getIdM().getIdM();
                         index++;
                         razl+=","; 
                }
               
            }
             razl+="#";
             
             
             //prihavtanje komitenta
            
            ObjectMessage om4=(ObjectMessage) komitentConsumer.receive();
            
            String sviKomitenti=(String) om4.getObject();
            
            String []komitenti=sviKomitenti.split("#");
              List<Komitent> komitenti1=new ArrayList<Komitent>();
            for (String string : komitenti) {
             //   System.out.println(string);
                String []komitent=string.split(",");
                Komitent k=new Komitent();
                k.setIdK(Integer.parseInt(komitent[0]));
                k.setNaziv(komitent[1]);
                k.setAdresa(komitent[2]);
                Mesto me=em.find(Mesto.class,Integer.parseInt(komitent[3]));
                  if(me==null){
                    System.out.println("jeste null");
                    me=new Mesto();
                    me.setIdM(Integer.parseInt(komitent[3]));
                
                }
                k.setIdM(me);
                
                komitenti1.add(k);
                
            
            }
            
           razl+="IdK\tNaziv\t\tAdresa\t\tIdM";
           razl+=",";
           
           
           

            List<Komitent> komitenti2=em.createNamedQuery("Komitent.findAll").getResultList();
            
            //dodala
            int broj2=komitenti2.size();
            for(int i=0;i<broj2;i++){
            
                if(komitenti1.get(i).getIdM().getIdM()!=komitenti2.get(i).getIdM().getIdM()){
                         razl+=komitenti1.get(i).getIdK()+"\t"+komitenti1.get(i).getNaziv()+"\t"+komitenti1.get(i).getAdresa()+"\t"+komitenti1.get(i).getIdM().getIdM();
                         razl+=","; 
                }
            }
            //dodala      
            
            
            if(komitenti1.size()>komitenti2.size()){
                int broj=komitenti1.size()-komitenti2.size();
                int index=komitenti1.size()-broj;
                for(int i=0;i<broj;i++){
                         razl+=komitenti1.get(index).getIdK()+"\t"+komitenti1.get(index).getNaziv()+"\t"+komitenti1.get(index).getAdresa()+"\t"+komitenti1.get(index).getIdM().getIdM();
                         index++;
                         razl+=","; 
                }
               
            }
             razl+="#";
             
             

            //System.out.println("PRIMIO PRVU BAZU U UPITU 6");
         
            
            JMSConsumer racunConsumer=c.createConsumer(odgovoriTema, "br=45",false);
            JMSConsumer transakcijaConsumer=c.createConsumer(odgovoriTema, "br=46",false);
            JMSConsumer uplataConsumer=c.createConsumer(odgovoriTema, "br=47",false);
            JMSConsumer isplataConsumer=c.createConsumer(odgovoriTema, "br=48",false);
            JMSConsumer prenosConsumer=c.createConsumer(odgovoriTema, "br=49",false);
          
           
            ObjectMessage om5=dohvKontekst().createObjectMessage();
            om5.setIntProperty("br", 44);
            
            dohvProducera().send(zahetviTema,om5);
            
           // System.out.println("poslao poruku podsistemu2!!!");
          
            //prihvatanje racuna
            ObjectMessage om6=(ObjectMessage) racunConsumer.receive();
            String sviRacuni=(String) om6.getObject();
                  List<Racun> racuni1=new ArrayList<Racun>();
            String []racuni=sviRacuni.split("#");
            for (String string : racuni) {
                String []racun=string.split(",");
                Racun r=new Racun();
                r.setIdR(Integer.parseInt(racun[0]));
                r.setIdM( Integer.parseInt(racun[1]));
                r.setStanje( Integer.parseInt(racun[2]));
                Komitent k=em.find(Komitent.class,  Integer.parseInt(racun[3]));
                if(k==null){
                    System.out.println("jeste null");
                    k=new Komitent();
                    k.setIdK(Integer.parseInt(racun[3]));
                
                }
                r.setIdK(k);
                r.setDozvoljeniMinus( Integer.parseInt(racun[4]));
                r.setStatus(racun[5]);
                r.setDatumIVremeOtvaranja(new Date(System.currentTimeMillis()));
                r.setBrTransakcija( Integer.parseInt(racun[7]));
                
                racuni1.add(r);
                
            }
            
            // System.out.println("PRIMIO racun U UPITU 6");
            
            razl+="IdR\tIdM\tStanje\tIdK\tDozMin\tStatus\tDatumIVremeKreiranja\t\tBrTransakcija";
           razl+=",";

            List<Racun> racuni2=em.createNamedQuery("Racun.findAll").getResultList();
            
            //dodala
            int broj3=racuni2.size();
            for(int i=0;i<broj3;i++){
            
                if(!racuni1.get(i).getStatus().equals(racuni2.get(i).getStatus()) || racuni1.get(i).getStanje()!=racuni2.get(i).getStanje() || racuni1.get(i).getBrTransakcija()!=racuni2.get(i).getBrTransakcija()){
                         razl+=racuni1.get(i).getIdR()+"\t"+racuni1.get(i).getIdM()+"\t"+racuni1.get(i).getStanje()+"\t"+racuni1.get(i).getIdK().getIdK()+"\t"+racuni1.get(i).getDozvoljeniMinus()+"\t"+racuni1.get(i).getStatus()+"\t"+racuni1.get(i).getDatumIVremeOtvaranja()+"\t"+racuni1.get(i).getBrTransakcija();
                         razl+=",";  
                }
            }
            //dodala 
            
            
            
            if(racuni1.size()>racuni2.size()){
                int broj=racuni1.size()-racuni2.size();
                int index=racuni1.size()-broj;
                for(int i=0;i<broj;i++){
                         razl+=racuni1.get(index).getIdR()+"\t"+racuni1.get(index).getIdM()+"\t"+racuni1.get(index).getStanje()+"\t"+racuni1.get(index).getIdK().getIdK()+"\t"+racuni1.get(index).getDozvoljeniMinus()+"\t"+racuni1.get(index).getStatus()+"\t"+racuni1.get(index).getDatumIVremeOtvaranja()+"\t"+racuni1.get(index).getBrTransakcija();
                         index++;
                         razl+=","; 
                }
               
            }
             razl+="#";
           
           //prihavtanje transakije
            
            ObjectMessage om7=(ObjectMessage) transakcijaConsumer.receive();
            
          //    System.out.println("PRIMIO transakcija U UPITU 6");
            
            String svetransakcije=(String) om7.getObject();
             List<Transakcija> transaksije1=new ArrayList<Transakcija>();
            String []transakcije=svetransakcije.split("#");
            for (String string : transakcije) {
               // System.out.println(string);
                String []transakcija=string.split(",");
                Transakcija t=new Transakcija();
                t.setIdT( Integer.parseInt(transakcija[0]));
                t.setIznos( Integer.parseInt(transakcija[1]));
                t.setDatumIVreme(new Date(System.currentTimeMillis()));
                t.setSvrha(transakcija[3]);
                t.setRedniBr( Integer.parseInt(transakcija[4]));
                
                Racun ra=em.find(Racun.class,  Integer.parseInt(transakcija[5]));
               //dodala
                if(ra==null){
                    System.out.println("jeste null");
                    ra=new Racun();
                    ra.setIdR(Integer.parseInt(transakcija[5]));
                
                }
                //dodala        
                t.setIdRac(ra);
                
                transaksije1.add(t);
                
            }
            
           razl+="IdT\tIznos\tDatumIVreme\t\tSvrha\tRedniBr\tIdRac";
           razl+=",";

            List<Transakcija> transakcije2=em.createNamedQuery("Transakcija.findAll").getResultList();
            if(transaksije1.size()>transakcije2.size()){
                int broj=transaksije1.size()-transakcije2.size();
                int index=transaksije1.size()-broj;
                for(int i=0;i<broj;i++){
                    
                           // System.out.println("broj="+broj);
                            //System.out.println("index="+index);
                           // System.out.println("idR="+transaksije1.get(index).getIdRac().getIdR());
                         razl+=transaksije1.get(index).getIdT()+"\t"+transaksije1.get(index).getIznos()+"\t"+transaksije1.get(index).getDatumIVreme()+"\t"+transaksije1.get(index).getSvrha()+"\t"+transaksije1.get(index).getRedniBr()+"\t"+transaksije1.get(index).getIdRac().getIdR();
                         index++;
                         razl+=","; 
                }
               
            }
             razl+="#";
            
           //prihavtanje uplata
            
            ObjectMessage om8=(ObjectMessage) uplataConsumer.receive();
            
            String sveUplate=(String) om8.getObject();
             List<Uplata> uplate1=new ArrayList<Uplata>();
            String []uplate=sveUplate.split("#");
            for (String string : uplate) {
              //  System.out.println(string);
                String []uplata=string.split(",");
                Transakcija t=em.find(Transakcija.class,  Integer.parseInt(uplata[0]));
                Uplata u=new Uplata();
                u.setIdT(Integer.parseInt(uplata[0]));
                u.setIdFil(Integer.parseInt(uplata[1]));
                
               uplate1.add(u);
                
            }
            
           razl+="IdT\tIdFil";
           razl+=",";

            List<Uplata> uplate2=em.createNamedQuery("Uplata.findAll").getResultList();
            if(uplate1.size()>uplate2.size()){
                int broj=uplate1.size()-uplate2.size();
                int index=uplate1.size()-broj;
                for(int i=0;i<broj;i++){
                         razl+=uplate1.get(index).getIdT()+"\t"+uplate1.get(index).getIdFil();
                         index++;
                         razl+=","; 
                }
               
            }
             razl+="#";
            
             //prihavtanje isplate
            
            ObjectMessage om9=(ObjectMessage) isplataConsumer.receive();
            
            String sveIsplate=(String) om9.getObject();
             List<Isplata> isplate1=new ArrayList<Isplata>();
            String []isplate=sveIsplate.split("#");
            for (String string : isplate) {
              //  System.out.println(string);
                String []isplata=string.split(",");
                Transakcija t=em.find(Transakcija.class,  Integer.parseInt(isplata[0]));
                Isplata i=new Isplata();
                i.setIdT(Integer.parseInt(isplata[0]));
                i.setIdFil( Integer.parseInt(isplata[1]));
                
               isplate1.add(i);
                
            }
            
              razl+="IdT\tIdFil";
           razl+=",";

            List<Isplata> isplate2=em.createNamedQuery("Isplata.findAll").getResultList();
            if(isplate1.size()>isplate2.size()){
                int broj=isplate1.size()-uplate2.size();
                int index=isplate1.size()-broj;
                for(int i=0;i<broj;i++){
                         razl+=isplate1.get(index).getIdT()+"\t"+isplate1.get(index).getIdFil();
                         index++;
                         razl+=","; 
                }
               
            }
             razl+="#";
            
   
                  //prihavtanje prenosa
            
            ObjectMessage om10=(ObjectMessage) prenosConsumer.receive();
              List<Prenos> prenosi1=new ArrayList<Prenos>();
            String sviPrenosi=(String) om10.getObject();
            
            String []prenosi=sviPrenosi.split("#");
            for (String string : prenosi) {
              //  System.out.println(string);
                String []prenos=string.split(",");
                Transakcija t=em.find(Transakcija.class,  Integer.parseInt(prenos[0]));
                Prenos p=new Prenos();
                p.setIdT(Integer.parseInt(prenos[0]));
                
                Racun sa=em.find(Racun.class, Integer.parseInt(prenos[1]));
                 if(sa==null){
                    System.out.println("jeste null");
                    sa=new Racun();
                    sa.setIdR(Integer.parseInt(prenos[1]));
                
                }
                p.setIdRacSa(sa);
                
                Racun na=em.find(Racun.class, Integer.parseInt(prenos[2]));
                 if(na==null){
                    System.out.println("jeste null");
                    na=new Racun();
                    na.setIdR(Integer.parseInt(prenos[2]));
                
                }
                p.setIdRacKa(na);
                
                prenosi1.add(p);
                
            } 
            
           razl+="IdT\tIdRacSa\tIdRacNa";
           razl+=",";

            List<Prenos> prenosi2=em.createNamedQuery("Prenos.findAll").getResultList();
            if(prenosi1.size()>prenosi2.size()){
                int broj=prenosi1.size()-uplate2.size();
                int index=prenosi1.size()-broj;
                for(int i=0;i<broj;i++){
                         razl+=prenosi1.get(index).getIdT()+"\t"+prenosi1.get(index).getIdRacSa().getIdR()+"\t"+prenosi1.get(index).getIdRacKa().getIdR();
                         index++;
                         razl+=","; 
                }
               
            }
             razl+="#";
             
            
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
     
            return razl;
     
     }
    
    
    
    
   public static void main(String[] args) {
       
        JMSContext c=dohvKontekst();
        JMSConsumer consumer=c.createConsumer(zahetviTema, "br=15 OR br=16");
       
            
       Thread primanjeZahteva=new Thread(()->{
            
       //primanje zahteva 15 i 16
      while(true){
         try {
                    ObjectMessage om=(ObjectMessage) consumer.receive();
                    int brojZahetva=om.getIntProperty("br");
                    
                     switch(brojZahetva){
                        case 15:
                                String s15=dohvSvihPodatakaIzRezervneKopije();
                                ObjectMessage om15=dohvKontekst().createObjectMessage(s15);
                                om15.setIntProperty("br",15);
                                dohvProducera().send(odgovoriTema, om15);
                            break;
                        case 16:
                            
                             String s16=dohvRazlike();
                             ObjectMessage om16=dohvKontekst().createObjectMessage(s16);
                             om16.setIntProperty("br",16);
                             dohvProducera().send(odgovoriTema, om16);
                            break;
                     }
                    
                    
                    
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
         
       }
            
     });
      
     primanjeZahteva.start();
        
      int i=1;
        while(true){
            
            System.out.println("poceo dohvatanje iz prve baze!");
            dohvatanjeBazaIzPodsistema1();
            System.out.println("zavrseno dohvatanje iz prve baze!");
            dohvatanjeBazaIzPodsistema2();
            System.out.println("zavrseno dohvatanje iz druge baze!");
            System.out.println(i);
            i++;
            
            
            try {
                //na svaka 2 min tj 120 sek
                Thread.sleep(120000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   
    }
    
}
