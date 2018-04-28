package com.login.auth.service;

import com.login.user.model.User;
import org.springframework.data.authentication.UserCredentials;

import java.security.InvalidKeyException;

/**
 * Service which provides operation for Authentication.
 *
 * @author Jemin
 */
public interface AuthService {

    /**
     * Issue the JWT.
     *
     * @param userEmail {@link User#emailAddress}
     * @return : JWT.
     * @throws InvalidKeyException
     */
    String issueToken(String userEmail) throws InvalidKeyException;

    /**
     * Validate the JWT.
     *
     * @param token : JWT.
     * @return : True of token in valid.
     * @throws InvalidKeyException
     */
    boolean isTokenValid(String token) throws InvalidKeyException;

    /**
     * Refresh the token. Increase the token validity.
     *
     * @param token :  JWT.
     * @return : Refresh JWT
     * @throws Exception : In case of refresh count is exceed the refresh limit.
     */
    String refreshToken(String token) throws Exception;

    /**
     * Validate the user credentials.
     *
     * @param userCredentials : {@link UserCredentials}
     * @return : True if credentials are correct.
     * @throws Exception
     */
    boolean authenticate(UserCredentials userCredentials) throws Exception;
}
