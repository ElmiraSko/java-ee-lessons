package ru.erasko;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import ru.erasko.ProductRepr;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Client client = ClientBuilder.newClient(new ClientConfig()
                .register(JacksonJsonProvider.class));
        WebTarget webTarget = client.target("http://localhost:8080/first-jsf-app/api")
                .path("product");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        String resp = response.readEntity(String.class);

        System.out.println(response.getStatus());
        System.out.println(resp);
        System.out.println("======================");

        Response response2 = invocationBuilder.get();
        List<ProductRepr> products = response2.readEntity(new GenericType<List<ProductRepr>>(){});

        for (ProductRepr pr: products) {
            System.out.println(pr.getId() + ": " + pr.getName() + ", " + pr.getPrice() + ", " + pr.getCategoryName());
        }
    }
}
