package com.login.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration for JWT based Authentication.
 * All configuration are provided in application.properties.
 *
 * @author : Jemin
 */
@Component
public class JWTConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiryTimeInMinutes}")
    private Integer expiryTimeInMinutes;

    @Value("${jwt.refreshLimitClaimName}")
    private String refreshLimitClaimName;

    @Value("${jwt.refreshLimit}")
    private Integer refreshLimit;

    @Value("${jwt.refreshCountClaimName}")
    private String refreshCountClaimName;


    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Integer getExpiryTimeInMinutes() {
        return expiryTimeInMinutes;
    }

    public void setExpiryTimeInMinutes(Integer expiryTimeInMinutes) {
        this.expiryTimeInMinutes = expiryTimeInMinutes;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshLimitClaimName() {
        return refreshLimitClaimName;
    }

    public void setRefreshLimitClaimName(String refreshLimitClaimName) {
        this.refreshLimitClaimName = refreshLimitClaimName;
    }

    public Integer getRefreshLimit() {
        return refreshLimit;
    }

    public void setRefreshLimit(Integer refreshLimit) {
        this.refreshLimit = refreshLimit;
    }

    public String getRefreshCountClaimName() {
        return refreshCountClaimName;
    }

    public void setRefreshCountClaimName(String refreshCountClaimName) {
        this.refreshCountClaimName = refreshCountClaimName;
    }
}