/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentskaAplikacija;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xxx
 */

public class Aplikacija {
    public static int selektovano=0;
    
    //PODSISTEM1
    public static void kreiranjeMesta(String naziv,int postanskiBroj){
        try {
         
            String param=naziv+","+postanskiBroj;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/mesto");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("POST");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static void kreiranjeFilijale(String naziv,String adresa,int idM){
        try {
         
            String param=naziv+","+adresa+","+idM;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/filijala");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("POST");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
     
    public static void kreiranjeKomitenta(String naziv,String adresa,int idM){
        try {
         
            String param=naziv+","+adresa+","+idM;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/komitent");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("POST");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static String dohvatanjeMesta() throws IOException{
    URL url=new URL("http://localhost:8080/centralniServerr/resources/mesto");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("GET");
            
            InputStreamReader isr=new InputStreamReader(myHttpConnection.getInputStream());
            
            BufferedReader bf=new BufferedReader(isr);
            
            String  mesta=bf.readLine(); 
  
        return mesta;
        
     }
        
    public static String dohvatanjeFilijala() throws IOException{
    URL url=new URL("http://localhost:8080/centralniServerr/resources/filijala");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("GET");
            
            InputStreamReader isr=new InputStreamReader(myHttpConnection.getInputStream());
            
            BufferedReader bf=new BufferedReader(isr);
            
            String  filijale=bf.readLine(); 
  
        return filijale;
        
     }
    
    public static String dohvatanjeKomitenata() throws IOException{
    URL url=new URL("http://localhost:8080/centralniServerr/resources/komitent");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("GET");
            
            InputStreamReader isr=new InputStreamReader(myHttpConnection.getInputStream());
            
            BufferedReader bf=new BufferedReader(isr);
            
            String  komitenti=bf.readLine(); 
  
        return komitenti;
        
     }
        
    public static void promenaSedistaZaKomitenta(String adresa,int idK,int idM){
        try {
         
            String param=idK+","+adresa+","+idM;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/komitent");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("PUT");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    //PODSISTEM2
    public static void otvaranjeRacuna(int idK,int idM,int dozMinus){
        try {
         
            String param=idK+","+idM+","+dozMinus;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/racun");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("POST");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static void zatvaranjeRacuna(int idR){
        try {
         
            String param=idR+"";
            URL url=new URL("http://localhost:8080/centralniServerr/resources/racun");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("PUT");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
       
      
    public static String dohvatanjeSvihRacunaZaKomitenta(int idK) throws IOException{
    URL url=new URL("http://localhost:8080/centralniServerr/resources/racun/"+idK);
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("GET");
            
            InputStreamReader isr=new InputStreamReader(myHttpConnection.getInputStream());
            
            BufferedReader bf=new BufferedReader(isr);
            
            String  racuni=bf.readLine(); 
  
        return racuni;
        
     }  
  
    public static void kreiranjeUplate(int iznos,int idR,String svrha,int idF){
        try {
         
            String param=iznos+","+idR+","+svrha+","+idF;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/transakcija/uplata");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("POST");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public static void kreiranjeIsplate(int iznos,int idR,String svrha,int idF){
        try {
         
            String param=iznos+","+idR+","+svrha+","+idF;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/transakcija/isplata");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("POST");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
     public static void kreiranjePrenosa(int iznos,int idRSa,int idRNa,String svrha){
        try {
         
            String param=iznos+","+idRSa+","+idRNa+","+svrha;
            URL url=new URL("http://localhost:8080/centralniServerr/resources/transakcija/prenos");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("POST");
            myHttpConnection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(myHttpConnection.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
            myHttpConnection.getResponseCode();
               
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
      
          
    public static String dohvatanjeSvihTransakcijaZaRacun(int idR) throws IOException{
    URL url=new URL("http://localhost:8080/centralniServerr/resources/transakcija/"+idR);
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("GET");
            
            InputStreamReader isr=new InputStreamReader(myHttpConnection.getInputStream());
            
            BufferedReader bf=new BufferedReader(isr);
            
            String  racuni=bf.readLine(); 
  
        return racuni;
        
     } 
   
    public static String dohvatanjeRezervneKopije() throws IOException{
            URL url=new URL("http://localhost:8080/centralniServerr/resources/kopija");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("GET");
            
            InputStreamReader isr=new InputStreamReader(myHttpConnection.getInputStream());
            
            BufferedReader bf=new BufferedReader(isr);
            
            String  mesta=bf.readLine(); 
  
        return mesta;
        
     }
    
      public static String dohvatanjeRazlika() throws IOException{
            URL url=new URL("http://localhost:8080/centralniServerr/resources/kopija/razlike");
            HttpURLConnection myHttpConnection = (HttpURLConnection) url.openConnection();
            myHttpConnection.setRequestMethod("GET");
            
            InputStreamReader isr=new InputStreamReader(myHttpConnection.getInputStream());
            
            BufferedReader bf=new BufferedReader(isr);
            
            String  mesta=bf.readLine(); 
  
        return mesta;
        
     }
     
    
    public static void main(String[] args) {
        
        
        Frame f=new Frame("Klijentska aplikacija");
        f.setSize(1500,1000);
        f.setVisible(true);
        
        Panel p1=new Panel();
        p1.setBounds(1000,1000,1000,1000);
        p1.setBackground(new Color(155,237,232));
        p1.setLayout(new GridLayout(1, 2));
        f.add(p1);
        
        TextArea ta=new TextArea();
        ta.setBackground(new Color(220,232,236));
        ta.setSize(500, 1000);
        
        Panel p2=new Panel();
        p2.setBounds(500,500,500,500);
        p2.setBackground(new Color(244,214,220));
        p2.setLayout(new BorderLayout());
        
        
        Panel p3=new Panel();
        p3.setLayout(new GridLayout(1, 1));
        p3.setBounds(500,500,500,500);
        p3.setBackground(new Color(204,204,204));
        p3.add(ta);
        
      
        
        p1.add(p2);
        p1.add(p3);
    
        Panel dugmad=new Panel();
        dugmad.setLayout(new GridLayout(5,1));
        
        Button close=new Button("ZATVORI APLIKACIJU");
        close.setBounds(50,50,100,100);
        close.addActionListener(e->{f.dispose();});
       
        Button izvrsi=new Button("IZVRSI FUNKCIONALNOST");
        izvrsi.setBounds(50,50,100,100);
        
        dugmad.add(izvrsi);
        dugmad.add(new Panel());
        dugmad.add(close);
       
        
        p2.add(dugmad,BorderLayout.SOUTH);
       
        
        Choice opcije=new Choice();
        opcije.add("");
        opcije.add("1. Kreiranje mesta(Naziv, PostanskiBroj)");
        opcije.add("2.Kreiranje filijale u mestu(NazivFilijale,AdresaFilijale,IdMesta)");
        opcije.add("3.Kreiranje komitenta(NazivKomitenta,AdresaKomitenta,IdMesta)");
        opcije.add("4.Promena sedista za zadatog komitenta(AdresaKomitenta,IdKomitenta,IdMesta)");
        opcije.add("5.Otvaranje racuna(IdMesta,IdKomitenta,DozvoljeniMinus)");
        opcije.add("6. Zatvaranje racuna(IdRacuna)");
        opcije.add("7.Kreiranje transakcija koja je prenos sume sa jednog racuna na drugi racun(Iznos,IdRacSa,IdRacNa,Svrha)");
        opcije.add("8. Kreiranje transakcija koja je uplata novca na racun(Iznos,IdRacuna,Svrha,IdFil)");
        opcije.add("9. Kreiranje transakcija koja je isplata novca sa racuna(Iznos,IdRacuna,Svrha,IdFil)");
        opcije.add("10. Dohvatanje svih mesta");
        opcije.add("11. Dohvatanje svih filijala");
        opcije.add("12. Dohvatanje svih komitenata");
        opcije.add("13. Dohvatanje svih racuna za komitenta(IdKomitenta)");
        opcije.add("14. Dohvatanje svih transakcija za racun(IdRacuna)");
        opcije.add("15. Dohvatanje svih podataka iz rezervne kopije");
        opcije.add("16. Dohvatanje razlike u podacima u originalnim i u rezervnoj kopiji");
             
     
        opcije.setBackground(new Color(214,252,253));
     
        Panel polja=new Panel();
       // polja.add(opcije);
        polja.setLayout(new GridLayout(20,2));
       // polja.add(new Label());
        
        polja.add(new Label("Naziv mesta: "));
        TextField nazivMesta=new TextField();
        nazivMesta.setBackground(new Color(244,231,236));
        polja.add(nazivMesta);
         
        
       
         polja.add(new Label("Postanski broj: "));
        TextField postanskiBroj=new TextField();
          postanskiBroj.setBackground(new Color(244,231,236));
        polja.add(postanskiBroj);
        
        
        
       polja.add(new Label("Naziv filijale: "));
        
        TextField nazivFilijale=new TextField();
          nazivFilijale.setBackground(new Color(244,231,236));
        polja.add(nazivFilijale);
          
         polja.add(new Label("Adresa filijale: "));
        TextField adresaFilijale=new TextField();
          adresaFilijale.setBackground(new Color(244,231,236));
        polja.add(adresaFilijale);
       
       polja.add(new Label("IdMesta: "));
        TextField idMesta=new TextField();
          idMesta.setBackground(new Color(244,231,236));
        polja.add(idMesta);
       
       
        polja.add(new Label("Naziv komitenta: "));
        TextField nazivKomitenta=new TextField();
        nazivKomitenta.setBackground(new Color(244,231,236));
        polja.add(nazivKomitenta);
         
        
     
        polja.add(new Label("Adresa komitenta: "));
        TextField adresaKomitenta=new TextField();
        adresaKomitenta.setBackground(new Color(244,231,236));
        polja.add(adresaKomitenta);
          
        
       polja.add(new Label("IdKomitenta: "));
        TextField idKomitenta=new TextField();
        idKomitenta.setBackground(new Color(244,231,236));
        polja.add(idKomitenta);
        
        
        polja.add(new Label("Dozvoljeni minus: "));
        TextField dozMinus=new TextField();
        dozMinus.setBackground(new Color(244,231,236));
        polja.add(dozMinus);
        
        polja.add(new Label("IdRacuna: "));
        TextField idRacuna=new TextField();
        idRacuna.setBackground(new Color(244,231,236));
        polja.add(idRacuna);
        
        
        polja.add(new Label("Iznos: "));
        TextField iznos=new TextField();
        iznos.setBackground(new Color(244,231,236));
        polja.add(iznos);
        
        polja.add(new Label("Svrha: "));
        TextField svrha=new TextField();
        svrha.setBackground(new Color(244,231,236));
        polja.add(svrha);
         
        polja.add(new Label("IdFilijale: "));
        TextField idFilijale=new TextField();
        idFilijale.setBackground(new Color(244,231,236));
        polja.add(idFilijale);  
        
        polja.add(new Label("IdRacunaSa: "));
        TextField idRacSa=new TextField();
        idRacSa.setBackground(new Color(244,231,236));
        polja.add(idRacSa);   
        
        polja.add(new Label("IdRacunaKa: "));
        TextField idRacKa=new TextField();
        idRacKa.setBackground(new Color(244,231,236));
        polja.add(idRacKa);   

        izvrsi.addActionListener(a->{
            
         
        
          switch(selektovano){
            case 1:
                
                String naziv=nazivMesta.getText();
                int posBr=Integer.parseInt(postanskiBroj.getText());
                kreiranjeMesta(naziv, posBr);
                
                ta.setText("Obradjen zahtev br1!");
                break;
            case 2:
                String naziv2=nazivFilijale.getText();
                String adresa2=adresaFilijale.getText();
                int idM=Integer.parseInt(idMesta.getText());
                kreiranjeFilijale(naziv2, adresa2,idM);
                
               ta.setText("Obradjen zahtev br3!\n(ako ne postoji mesto za zadatim idMesta, funkcionalnost nema efekta!)");
                break;
            case 3:
                String naziv3=nazivKomitenta.getText();
                String adresa3=adresaKomitenta.getText();
                int idM3=Integer.parseInt(idMesta.getText());
                kreiranjeKomitenta(naziv3, adresa3,idM3);
                
               ta.setText("Obradjen zahtev br2!\n(ako ne postoji mesto za zadatim idMesta, funkcionalnost nema efekta!)");
                break;
             case 4:
                String adresa4=adresaKomitenta.getText();
                int idM4=Integer.parseInt(idMesta.getText());
                int idK4=Integer.parseInt(idKomitenta.getText());
                 promenaSedistaZaKomitenta(adresa4, idK4, idM4);
                
               ta.setText("Obradjen zahtev br3!\n(ako ne postoji mesto za zadatim idMesta ili komitent sa zadatim idKomitenta, funkcionalnost nema efekta!)");
                break;
                
            case 5:
                
               
                int idK5=Integer.parseInt(idKomitenta.getText());
                int idM5=Integer.parseInt(idMesta.getText());
                int dozMin5=Integer.parseInt(dozMinus.getText());
                
                otvaranjeRacuna(idK5, idM5, dozMin5);
                
                ta.setText("Obradjen zahtev br5!(ako ne postoji mesto za zadatim idMesta ili komitent sa zadatim idKomitenta, funkcionalnost nema efekta!)");
                break;
                
            case 6:
                int idR6=Integer.parseInt(idRacuna.getText());
                zatvaranjeRacuna(idR6);
                
                ta.setText("Obradjen zahtev br6!(ako ne postoji racun sa zadatim idRacuna, funkcionalnost nema efekta!)");
                break;
            case 7:
               int iznos7=Integer.parseInt(iznos.getText());
               int idRSa7=Integer.parseInt(idRacSa.getText());
                int idRNa7=Integer.parseInt(idRacKa.getText());
               String  svrha7=svrha.getText();
              
                
               kreiranjePrenosa(iznos7, idRSa7, idRNa7, svrha7);
               ta.setText("Obradjen zahtev br7!\n(ako ne postoji racunSa ili racunKa ili ako je racunSa blokiran ili zatvoren ili ako je racunKa zatvoren, funkcionalnost nema efekta!)");
                break;      
                
            case 8:
               int iznos8=Integer.parseInt(iznos.getText());
               int idR8=Integer.parseInt(idRacuna.getText());
               String  svrha8=svrha.getText();
               int idF8=Integer.parseInt(idFilijale.getText());
                
               kreiranjeUplate(iznos8, idR8, svrha8, idF8);
               ta.setText("Obradjen zahtev br8!\n(ako ne postoji racun ili filijala ili ako je racun zatvoren, funkcionalnost nema efekta!)");
                break;
            case 9:
               int iznos9=Integer.parseInt(iznos.getText());
               int idR9=Integer.parseInt(idRacuna.getText());
               String  svrha9=svrha.getText();
               int idF9=Integer.parseInt(idFilijale.getText());
                
               kreiranjeIsplate(iznos9, idR9, svrha9, idF9);
               ta.setText("Obradjen zahtev br9!\n(ako ne postoji racun ili filijala ili ako je racun zatvoren ili blokiran, funkcionalnost nema efekta!)");
                break;    
            case 10:
                ta.setFont(new Font("Arial", Font.ITALIC, 12));
                ta.setText("IdM\tNaziv\tPostanskiBroj\n\n");
                ta.setFont(new Font("Arial", Font.BOLD, 12));
                //ta.setText(ta.getText()+"jana");
              {
                  try {
                      String mesta=dohvatanjeMesta();
                      String[] redovi=mesta.split(",");
                      for (String red : redovi) {
                          ta.setText(ta.getText()+red+"\n");
                          
                      }
                  } catch (IOException ex) {
                      Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
                break;
                
              case 11:
                ta.setFont(new Font("Arial", Font.ITALIC, 12));
                ta.setText("IdF\tNaziv\t\tAdresa\t\t\tIdM\n\n");
                ta.setFont(new Font("Arial", Font.BOLD, 12));
                //ta.setText(ta.getText()+"jana");
              {
                  try {
                      String filijale=dohvatanjeFilijala();
                      String[] redovi=filijale.split(",");
                      for (String red : redovi) {
                          ta.setText(ta.getText()+red+"\n");
                          
                      }
                  } catch (IOException ex) {
                      Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
                break;

                case 12:
                ta.setFont(new Font("Arial", Font.ITALIC, 12));
                ta.setText("IdK\tNaziv\t\tAdresa\t\tIdM\n\n");
                ta.setFont(new Font("Arial", Font.BOLD, 12));
                //ta.setText(ta.getText()+"jana");
              {
                  try {
                      String komitenti=dohvatanjeKomitenata();
                      String[] redovi=komitenti.split(",");
                      for (String red : redovi) {
                          ta.setText(ta.getText()+red+"\n");
                          
                      }
                  } catch (IOException ex) {
                      Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
                break;
                case 13:
                ta.setFont(new Font("Arial", Font.ITALIC, 12));
                ta.setText("IdR\tIdM\tStanje\tIdK\tDozMin\tStatus\tDatumIVremeKreiranja\t\tBrTransakcija\n\n");
                ta.setFont(new Font("Arial", Font.BOLD, 12));
                //ta.setText(ta.getText()+"jana");
              {
                  try {
                      int idK13=Integer.parseInt(idKomitenta.getText());
                      String racuni=dohvatanjeSvihRacunaZaKomitenta(idK13);
                      String[] redovi=racuni.split(",");
                      for (String red : redovi) {
                          ta.setText(ta.getText()+red+"\n");
                          
                      }
                  } catch (IOException ex) {
                      Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
                break;
                
              case 14:
                ta.setFont(new Font("Arial", Font.ITALIC, 12));
                ta.setText("IdT\tIznos\tDatumIVreme\t\tSvrha\tRedniBr\tIdRac\n\n");
                ta.setFont(new Font("Arial", Font.BOLD, 12));
                //ta.setText(ta.getText()+"jana");
              {
                  try {
                      int idR14=Integer.parseInt(idRacuna.getText());
                      String transakcije=dohvatanjeSvihTransakcijaZaRacun(idR14);
                      String[] redovi=transakcije.split(",");
                      for (String red : redovi) {
                          ta.setText(ta.getText()+red+"\n");
                          
                      }
                  } catch (IOException ex) {
                      Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
                break;
                
               case 15:
                ta.setFont(new Font("Arial", Font.ITALIC, 12));
                ta.setFont(new Font("Arial", Font.BOLD, 12));
                //ta.setText(ta.getText()+"jana");
              {
                  try {
                      String kopija=dohvatanjeRezervneKopije();
                      String []tabele=kopija.split("#");
                      for (String tabela : tabele) {
                            String[] redovi=tabela.split(",");
                           for (String red : redovi) {
                               ta.setText(ta.getText()+red+"\n");
                          
                            }
                             ta.setText(ta.getText()+"\n\n\n");
                      }
                      
                  } catch (IOException ex) {
                      Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
                break;
                
                
              case 16:
                ta.setFont(new Font("Arial", Font.ITALIC, 12));
                ta.setFont(new Font("Arial", Font.BOLD, 12));
                //ta.setText(ta.getText()+"jana");
              {
                  try {
                      String razlika=dohvatanjeRazlika();
                      String []tabele=razlika.split("#");
                      for (String tabela : tabele) {
                            String[] redovi=tabela.split(",");
                           for (String red : redovi) {
                               ta.setText(ta.getText()+red+"\n");
                          
                            }
                             ta.setText(ta.getText()+"\n\n\n");
                      }
                      
                  } catch (IOException ex) {
                      Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
                break;
             
        }
        
      
        });
        
           opcije.addItemListener(il->{
        
            
                ta.setText("");
                nazivMesta.setText("");
                postanskiBroj.setText("");
                nazivFilijale.setText("");
                adresaFilijale.setText("");
                idMesta.setText("");
                adresaKomitenta.setText("");
                nazivKomitenta.setText("");
                idKomitenta.setText("");
                dozMinus.setText("");
                idRacuna.setText("");
                iznos.setText("");
               svrha.setText("");
               idFilijale.setText("");
               idRacKa.setText("");
               idRacSa.setText("");
                selektovano=opcije.getSelectedIndex();
      
        });
            
        
      
         close.setBackground(new Color(204,212,236));
         close.setFont(new Font("Arial", Font.BOLD, 12));
         
         
          izvrsi.setBackground(new Color(204,212,236));
         izvrsi.setFont(new Font("Arial", Font.BOLD, 12));
         
        p2.add(opcije,BorderLayout.NORTH);
        p2.add(polja);
        f.setVisible(true);
    
    
    
    }
    
}
