/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserverr.resources;

import static com.mycompany.centralniserverr.resources.EndPointMesto.context;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author xxx
 */

@Path("transakcija")
public class EndPointTransakcija {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
      
    @Resource(lookup="zahtev")
    public Topic zahetviTema;
    
    @Resource(lookup="odgovor")
    public Topic odgovoriTema;
    
    
    @Path("uplata")
    @POST
    public Response kreiranjeUplate(String parametri){
        
        
          
        try {
            if(context==null)context=cf.createContext();
            
            JMSProducer producer=context.createProducer();
            
            //prvo ide provera da li postoji zadata filijala u podsistemu1, ako postoji izvrsi sve ostalo, naknadno se proverava i da li postoji racun
            
            
            String[] s=parametri.split(",");
            String idFil=s[3];
            
            ObjectMessage om1=context.createObjectMessage(idFil);
            om1.setIntProperty("br", 20);
            
            producer.send(zahetviTema,om1);
            
            //primanje poruke da li postoji filijala
            
            
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=20");
            ObjectMessage om2=(ObjectMessage) consumer.receive();
            boolean postoji=om2.getBooleanProperty("postoji");
            
            
            //slanje zahteva za kreiranje uplate PODSISTEMU2
            
            if(postoji){
                        ObjectMessage om=context.createObjectMessage(parametri);
            
                        om.setIntProperty("br",8);
            
                        producer.send(zahetviTema, om);
            }
          
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }
          return Response.status(200).build();
    
    }
    
     
    @Path("isplata")
    @POST
    public Response kreiranjeIsplate(String parametri){
        
        
          
        try {
            if(context==null)context=cf.createContext();
            
            JMSProducer producer=context.createProducer();
            
            //prvo ide provera da li postoji zadata filijala u podsistemu1, ako postoji izvrsi sve ostalo, naknadno se proverava i da li postoji racun
            
            
            String[] s=parametri.split(",");
            String idFil=s[3];
            
            ObjectMessage om1=context.createObjectMessage(idFil);
            om1.setIntProperty("br", 20);
            
            producer.send(zahetviTema,om1);
            
            //primanje poruke da li postoji filijala
            
            
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=20");
            ObjectMessage om2=(ObjectMessage) consumer.receive();
            boolean postoji=om2.getBooleanProperty("postoji");
            
            
            //slanje zahteva za kreiranje isplate PODSISTEMU2
            
            if(postoji){
                        ObjectMessage om=context.createObjectMessage(parametri);
            
                        om.setIntProperty("br",9);
            
                        producer.send(zahetviTema, om);
            }
          
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }
          return Response.status(200).build();
    
    }
    
    @GET
    @Path("{idR}")
    public Response dohvatanjeSvihTranakcijaZaRacun(@PathParam("idR") int idR) throws JMSException{
        try {
            if(context==null)context=cf.createContext();
            JMSProducer producer=context.createProducer();
            String param=idR+"";
            ObjectMessage om=context.createObjectMessage(param);
            om.setIntProperty("br", 14);
            producer.send(zahetviTema, om);
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }    
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=14 ");
            ObjectMessage om1=(ObjectMessage) consumer.receive();
            String s=(String) om1.getObject();
             
        return Response.status(200).entity(s).build();
    
    }

     
    @Path("prenos")
    @POST
    public Response kreiranjePrenosa(String parametri){
        
        
          
        try {
            if(context==null)context=cf.createContext();
            
            JMSProducer producer=context.createProducer();
            
            //slanje zahteva za kreiranje uplate PODSISTEMU2
            
            ObjectMessage om=context.createObjectMessage(parametri);
            
            om.setIntProperty("br",7);
            
            producer.send(zahetviTema, om);
            
          
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }
          return Response.status(200).build();
    
    }






    
}
