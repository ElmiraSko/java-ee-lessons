package ru.erasko.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.service.interf.ProductService;
import ru.erasko.service.repr.ProductRepr;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/productQueue"),
        @ActivationConfigProperty(propertyName = "action = 'create'", propertyValue = "messageSelector")})
public class JmsProductBean implements MessageListener {

    Logger logger = LoggerFactory.getLogger(JmsProductBean.class);

    @EJB
    private ProductService productService;

    @Override
    public void onMessage(Message message) {
        logger.info("New JMS message");
        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage)message;
            try {
                ProductRepr productRepr = (ProductRepr) om.getObject();
                productService.insert(productRepr);

            } catch (JMSException ex) {
                logger.error("" + ex);
            }

        }

    }
}
