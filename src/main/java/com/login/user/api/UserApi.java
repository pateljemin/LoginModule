package com.login.user.api;

import com.login.user.model.User;
import com.login.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS resource class for {@link User}.
 *
 * @author : Jemin
 */
@Component
@Path("users")
public class UserApi {

    private final UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get fullName of user from email Address.
     *
     * @param email : {@link User#emailAddress}
     * @return : Full Name of User.
     */
    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("email") String email) throws Exception {

        User user = userService.find(email);
        if (user == null) {
            throw new Exception();
        }
        return Response.ok(user.getFirstName() + user.getLastName()).build();
    }

    /**
     * Save User.
     *
     * @param user : {@link User}
     * @return : User with ID.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) throws Exception {
        userService.save(user);
        return Response.ok(user).build();
    }
}
