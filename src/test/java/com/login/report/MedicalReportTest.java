package com.login.report;

import com.login.AbstractApiTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;

/**
 * Test for Medical report resource.
 *
 * @author Jemin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicalReportTest extends AbstractApiTest {

    @Test
    public void testAllReportWithAuth() {
        Response response = client.target(baseUri).path("report").request().header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader(getToken())).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertNotNull(response.getEntity());
    }

    @Test
    public void testAllReportWithOutAuth() {
        Response response = client.target(baseUri).path("report").request().get();
        Assert.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

}
