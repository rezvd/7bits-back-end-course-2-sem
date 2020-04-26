package it.sevenbits.hwspring.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Settings to the JWT token
 */
@Component
public class JwtSettings {

    private final String tokenIssuer;
    private final String tokenSigningKey;
    private final int aTokenDuration;

    /**
     * Constructor for JwtSettings
     * @param tokenIssuer is an issuer to sign token
     * @param tokenSigningKey is a key to sign token
     * @param aTokenDuration is a time within token is alive
     */
    public JwtSettings(@Value("${jwt.issuer}") final String tokenIssuer,
                       @Value("${jwt.signingKey}") final String tokenSigningKey,
                       @Value("${jwt.aTokenDuration}") final int aTokenDuration) {
        this.tokenIssuer = tokenIssuer;
        this.tokenSigningKey = tokenSigningKey;
        this.aTokenDuration = aTokenDuration;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public byte[] getTokenSigningKey() {
        return tokenSigningKey.getBytes(StandardCharsets.UTF_8);
    }

    public Duration getTokenExpiredIn() {
        return Duration.ofMinutes(aTokenDuration);
    }

}
