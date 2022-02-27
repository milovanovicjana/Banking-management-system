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
import javax.ws.rs.core.Response;

/**
 *
 * @author xxx
 */

@Path("komitent")
public class EndPointKomitent {
     
    @Resource(lookup="jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
      
    @Resource(lookup="zahtev")
    public Topic zahetviTema;
    
    @Resource(lookup="odgovor")
    public Topic odgovoriTema;
    
    
   @POST
    public Response kreiranjeKomitenta(String parametri){
        
        
        try {
            if(context==null)context=cf.createContext();
            
            JMSProducer producer=context.createProducer();
            ObjectMessage om=context.createObjectMessage(parametri);
            
           om.setIntProperty("br", 3);
            
            producer.send(zahetviTema, om);
            
          
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }
          return Response.status(200).build();
    }
    
    @GET
    public Response dohvatanjeSvihKomitenata() throws JMSException{
        try {
            if(context==null)context=cf.createContext();
            JMSProducer producer=context.createProducer();
            ObjectMessage om=context.createObjectMessage();
            om.setIntProperty("br", 12);
            producer.send(zahetviTema, om);
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }    
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=12");
            ObjectMessage om1=(ObjectMessage) consumer.receive();
            String s=(String) om1.getObject();
             
        return Response.status(200).entity(s).build();
    
    }
    
    
    //unosi se adresa,IdM,IdK
    @PUT
    public Response promenaSedistaKomitenta(String parametri){
        
        
        try {
            if(context==null)context=cf.createContext();
            
            JMSProducer producer=context.createProducer();
            ObjectMessage om=context.createObjectMessage(parametri);
            
           om.setIntProperty("br", 4);
           
            
            producer.send(zahetviTema, om);
            
          
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }
          return Response.status(200).build();
    }
    
}
