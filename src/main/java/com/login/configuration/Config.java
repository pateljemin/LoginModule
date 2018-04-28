package com.login.configuration;

import com.login.auth.api.AuthenticationApi;
import com.login.auth.filter.JwtAuthenticationFilter;
import com.login.report.api.MedicalApi;
import com.login.user.api.UserApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;


/**
 * Configuration class.
 *
 * @author Jemin
 */
@Component
@ApplicationPath("api")
public class Config extends ResourceConfig {

    public Config() {
        register(MedicalApi.class);
        register(UserApi.class);
        register(AuthenticationApi.class);
        register(JwtAuthenticationFilter.class);
    }
}