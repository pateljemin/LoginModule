package com.login.auth.api;

import com.login.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS resource class for Authentication.
 *
 * @author : Jemin
 */
@Component
@Path("token")
public class AuthenticationApi {

    @Autowired
    private AuthService authService;

    /**
     * Validate user credentials and issue a token for the user.
     *
     * @return JWT
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(@FormParam("username") String userName, @FormParam("password") String password) {

        try {
            UserCredentials userCredentials = new UserCredentials(userName, password);
            // Authenticate the user using the credentials provided
            boolean isAuthenticate = authService.authenticate(userCredentials);

            if (!isAuthenticate) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            // Issue a token for the user
            String token = authService.issueToken(userCredentials.getUsername());

            // Return the token on the response
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /**
     * Refresh the token.
     *
     * @return Refreshed JWT.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("refresh")
    public Response refresh(String token) {

        try {
            token = authService.refreshToken(token);

            // Return the refresh token on the response
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

}
