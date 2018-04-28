package com.login.auth.filter;

import com.login.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter for JWT based authentication.
 * <p>
 * JWT must be sent in Authorization header and prefix with "Bearer ".
 * Ex . Authorization :  Bearer header.claims.signature
 *
 * @author Jemin
 */
@Provider
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    private static final String JWT_HEADER_PREFIX = "Bearer ";
    private static final List<String> RESOURCES_TO_BYPASS = new ArrayList<>(); // All paths that don't require authentication.

    private final AuthService authService;

    @Context
    private UriInfo info;

    @Autowired
    public JwtAuthenticationFilter(AuthService authService) {
        this.authService = authService;
        RESOURCES_TO_BYPASS.add("token");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (RESOURCES_TO_BYPASS.stream().anyMatch(s -> info.getPath().startsWith(s))) {
            return;
        }
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(JWT_HEADER_PREFIX)) {
            String authenticationToken = authorizationHeader.substring(JWT_HEADER_PREFIX.length());
            try {
                boolean isValid = authService.isTokenValid(authenticationToken);
                if (!isValid) {
                    abortRequest(requestContext);
                }
            } catch (Exception e) {
                abortRequest(requestContext);
            }
        } else {
            abortRequest(requestContext);
        }
    }

    private void abortRequest(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, JWT_HEADER_PREFIX).build());
    }
}
