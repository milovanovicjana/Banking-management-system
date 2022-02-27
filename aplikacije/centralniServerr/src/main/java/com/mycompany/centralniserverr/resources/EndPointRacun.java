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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author xxx
 */

@Path("racun")
public class EndPointRacun {
    
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
      
    @Resource(lookup="zahtev")
    public Topic zahetviTema;
    
    @Resource(lookup="odgovor")
    public Topic odgovoriTema;
    
    
    @POST
    public Response otvaranjeRacuna(String parametri){
        
        
          
        try {
            if(context==null)context=cf.createContext();
            
            JMSProducer producer=context.createProducer();
            
            //prvo ide provera da li postoji zadato mesto u podsistemu1, ako postoji izvrsi sve ostalo, a provera da li postoji komitent je u podsistemu2
            
            
            String[] s=parametri.split(",");
            String idM=s[1];
            
            ObjectMessage om1=context.createObjectMessage(idM);
            om1.setIntProperty("br", 19);
            
            producer.send(zahetviTema,om1);
            
            //primanje poruke da li postoji mesto
            
            
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=19");
            ObjectMessage om2=(ObjectMessage) consumer.receive();
            boolean postoji=om2.getBooleanProperty("postoji");
            
            
            //slanje zahteva za kreiranje racuna PODSISTEMU2
            
            if(postoji){
                        ObjectMessage om=context.createObjectMessage(parametri);
            
                        om.setIntProperty("br", 5);
            
                        producer.send(zahetviTema, om);
            }
          
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }
          return Response.status(200).build();
    
    }
    
    
     
    @PUT
    public Response zatvaranjeRacuna(String parametri){
        
        
        try {
            if(context==null)context=cf.createContext();
            
            JMSProducer producer=context.createProducer();
            ObjectMessage om=context.createObjectMessage(parametri);
            
           om.setIntProperty("br", 6);
           
            
            producer.send(zahetviTema, om);
            
          
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }
          return Response.status(200).build();
    }
    
    @GET
    @Path("{idK}")
    public Response dohvatanjeSvihRacunaZaKomitenta(@PathParam("idK") int idK) throws JMSException{
        try {
            if(context==null)context=cf.createContext();
            JMSProducer producer=context.createProducer();
            String param=idK+"";
            ObjectMessage om=context.createObjectMessage(param);
            om.setIntProperty("br", 13);
            producer.send(zahetviTema, om);
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }    
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=13");
            ObjectMessage om1=(ObjectMessage) consumer.receive();
            String s=(String) om1.getObject();
             
        return Response.status(200).entity(s).build();
    
    }
    
    
}
