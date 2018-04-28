package com.login.auth;

import com.login.AbstractApiTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Test for Authentication resource.
 *
 * @author Jemin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest extends AbstractApiTest {

    @Test
    public void authWithValidDetails() {
        Form form = new Form();
        form.param("username", "pateljeminb@gmail.com");
        form.param("password", "hello123");
        String token = client.target(baseUri).path("token").request().post(Entity.form(form), String.class);
        Assert.assertNotNull(token);
    }

    @Test
    public void authWithInValidDetails() {
        Form form = new Form();
        form.param("username", "pateljeminb@gmail.com");
        form.param("password", "hello123234");
        Response response = client.target(baseUri).path("token").request().post(Entity.form(form));
        Assert.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void refreshToken() {
        Response response = client.target(baseUri).path("token").path("refresh").request().post(Entity.entity(getToken(), MediaType.APPLICATION_JSON));
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertNotNull(response.getEntity());
    }
}
