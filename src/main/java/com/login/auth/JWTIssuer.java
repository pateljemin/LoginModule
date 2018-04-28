package com.login.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Component which provide operations for JWT.
 *
 * @author : Jemin
 */
@Component
public class JWTIssuer {

    private final JWTConfiguration configuration;

    @Autowired
    public JWTIssuer(JWTConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Issue JWT token.
     *
     * @param userEmail           :  User E-mail.
     * @param currentRefreshCount : CurrentRefreshCount.
     * @return : JWT.
     * @throws InvalidKeyException
     */
    public String issueToken(String userEmail, Integer currentRefreshCount) throws InvalidKeyException {

        return Jwts.builder()
                .setIssuer(configuration.getIssuer())
                .setAudience(configuration.getAudience())
                .setSubject(userEmail)
                .claim(configuration.getRefreshCountClaimName(), currentRefreshCount)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(configuration.getExpiryTimeInMinutes())))
                .signWith(SignatureAlgorithm.HS256, configuration.getSecret())
                .compact();
    }

    /**
     * Check JWT is valid or not.
     *
     * @param token : JWT.
     * @return : True if JWT is valid.
     * @throws InvalidKeyException
     */
    public boolean isValidToken(String token) throws InvalidKeyException {
        parseToken(token);
        return true;
    }

    /**
     * Refresh the given JWT. Increse the Expiry time of token and increase refresh count.
     *
     * @param token :  JWT which needs to refresh.
     * @return : New JWT.
     * @throws Exception : In case of refresh count exceeds limit.
     */
    public String refreshToken(String token) throws Exception {
        Claims claims = parseToken(token);
        Integer count = (Integer) claims.get(configuration.getRefreshCountClaimName());
        if (count > configuration.getRefreshLimit()) {
            throw new Exception("Refresh token limit reached");
        }
        return issueToken(claims.getSubject(), count + 1);
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .requireIssuer(configuration.getIssuer())
                .setSigningKey(configuration.getSecret())
                .requireAudience(configuration.getAudience())
                .parseClaimsJws(token)
                .getBody();
    }
}
