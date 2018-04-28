package com.login;

import org.junit.Before;
import org.springframework.boot.web.server.LocalServerPort;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Base class of REST api test.
 *
 * @author Jemin
 */
public class AbstractApiTest {

    @LocalServerPort
    protected int port;

    protected URI baseUri;

    protected Client client;

    @Before
    public void setUp() throws URISyntaxException {
        this.baseUri = new URI("http://localhost:" + port + "/api");
        this.client = ClientBuilder.newClient();
    }

    protected String getToken() {
        Form form = new Form();
        form.param("username", "pateljeminb@gmail.com");
        form.param("password", "hello123");
        return client.target(baseUri).path("token").request().post(Entity.form(form), String.class);
    }

    protected String getAuthorizationHeader(String authenticationToken) {
        return "Bearer" + " " + authenticationToken;
    }
}
