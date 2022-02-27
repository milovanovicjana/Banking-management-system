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
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author xxx
 */


@Path("kopija")
public class EndPointRezervnaKopiija {
    
      
    @Resource(lookup="jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
      
    @Resource(lookup="zahtev")
    public Topic zahetviTema;
    
    @Resource(lookup="odgovor")
    public Topic odgovoriTema;
    
    
    
    @GET
    public Response dohvatanjeSvihPodatakaIzRezervneKopije() throws JMSException{
        try {
            if(context==null)context=cf.createContext();
            JMSProducer producer=context.createProducer();
            ObjectMessage om=context.createObjectMessage();
            om.setIntProperty("br", 15);
            producer.send(zahetviTema, om);
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }    
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=15");
            ObjectMessage om1=(ObjectMessage) consumer.receive();
            String s=(String) om1.getObject();
             
        return Response.status(200).entity(s).build();
    
    }
    
    @Path("razlike")
    @GET
    public Response dohvatanjeRazlika() throws JMSException{
        try {
            if(context==null)context=cf.createContext();
            JMSProducer producer=context.createProducer();
            ObjectMessage om=context.createObjectMessage();
            om.setIntProperty("br", 16);
            producer.send(zahetviTema, om);
        } catch (JMSException ex) {
            Logger.getLogger(EndPointMesto.class.getName()).log(Level.SEVERE, null, ex);
        }    
            JMSConsumer consumer=context.createConsumer(odgovoriTema,"br=16");
            ObjectMessage om1=(ObjectMessage) consumer.receive();
            String s=(String) om1.getObject();
             
        return Response.status(200).entity(s).build();
    
    }
    
}
