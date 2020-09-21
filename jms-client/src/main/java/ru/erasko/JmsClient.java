package ru.erasko;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

public class JmsClient {
    public static void main(String[] args) throws IOException, NamingException {

        Context context = createInitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
        JMSContext jmsContext = connectionFactory.createContext("jmsUser", "jmsuser");

        Destination dest = (Destination) context.lookup("jms/queue/productQueue");

        JMSProducer jmsProducer = jmsContext.createProducer();
        ObjectMessage message = jmsContext.createObjectMessage(
                new ProductRepr(null, "JMSProduct", "JMSdescription", new BigDecimal(5543), 2L, "Tablet"));

        jmsProducer.setProperty("action", "create").send(dest, message);
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties properties = new Properties();
        properties.load(JmsClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(properties);
    }
}
