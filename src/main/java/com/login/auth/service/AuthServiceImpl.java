package com.login.auth.service;

import com.login.user.model.User;
import com.login.auth.JWTIssuer;
import com.login.auth.PasswordUtil;
import com.login.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;

/**
 * Implementation for {@link AuthService}
 *
 * @author Jemin
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final JWTIssuer jwtIssuer;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(JWTIssuer jwtIssuer, UserService userService) {
        this.jwtIssuer = jwtIssuer;
        this.userService = userService;
    }

    @Override
    public String issueToken(String userEmail) throws InvalidKeyException {
        return jwtIssuer.issueToken(userEmail, 0);
    }

    @Override
    public boolean isTokenValid(String token) throws InvalidKeyException {
        return jwtIssuer.isValidToken(token);
    }

    @Override
    public String refreshToken(String token) throws Exception {
        return jwtIssuer.refreshToken(token);
    }

    @Override
    public boolean authenticate(UserCredentials userCredentials) throws Exception {
        final User user = userService.find(userCredentials.getUsername());
        if (user == null) {
            throw new Exception("User Email Address or Password invalid"); // Always show either password or Email is invalid. So hacker does not know which one valid.
        }
        final String password = user.getPassword();
        return PasswordUtil.check(userCredentials.getPassword(), password);
    }
}

