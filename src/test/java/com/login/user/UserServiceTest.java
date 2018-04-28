package com.login.user;

import com.login.AbstractApiTest;
import com.login.auth.PasswordUtil;
import com.login.user.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Test for User resource.
 *
 * @author Jemin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest extends AbstractApiTest {

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setFirstName("Abc");
        user.setLastName("cdb");
        user.setEmailAddress("abc@cde.com");
        user.setPassword(PasswordUtil.getSaltedHash("1234"));
        Response response = client.target(baseUri).path("users").request().header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader(getToken())).post(Entity.entity(user, MediaType.APPLICATION_JSON));
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertNotNull(response.getEntity());
    }

    @Test
    public void testFetchUser() {
        Response response = client.target(baseUri).path("users").path("abc@cde.com").request().header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader(getToken())).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertNotNull(response.getEntity());
    }

    @Test
    public void testCreateUseWithOutAuth() throws Exception {
        User user = new User();
        user.setFirstName("Abc");
        user.setLastName("cdb");
        user.setEmailAddress("abc@cde.com");
        user.setPassword(PasswordUtil.getSaltedHash("1234"));
        Response response = client.target(baseUri).path("users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        Assert.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFetchUserWithOutAuth() {
        Response response = client.target(baseUri).path("users").path("abc@cde.com").request().get();
        Assert.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
