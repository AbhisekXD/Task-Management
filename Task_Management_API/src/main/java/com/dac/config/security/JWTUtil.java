package com.dac.config.security;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTimeMillis;


    public String generateToken(UUID id, String userName, String role) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("id", id.toString())
                .withClaim("userName", userName)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .withIssuer("Dealers Auto Center")
                .sign(Algorithm.HMAC256(secret));
    }


    public Map<String, Object> validateTokenAndRetrieveClaims(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Dealers Auto Center")
                .build();

        DecodedJWT jwt = verifier.verify(token);

        return Map.of(
                "id", UUID.fromString(jwt.getClaim("id").asString()),
                "userName", jwt.getClaim("userName").asString(),
                "role", jwt.getClaim("role").asString()
        );
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        String usernameFromToken = getUsernameFromToken(token);
        return usernameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

  
    public String getUsernameFromToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Dealers Auto Center")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userName").asString();
    }


    private boolean isTokenExpired(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Dealers Auto Center")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getExpiresAt().before(new Date());
    }
}
