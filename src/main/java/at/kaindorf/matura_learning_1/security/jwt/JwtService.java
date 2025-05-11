package at.kaindorf.matura_learning_1.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

/**
 * JwtService - 10.05.2025
 *
 * @author david
 */


public class JwtService {
    private String jwtSecret;

    private Duration duration;

    private SecretKey getSigningKey(){
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        return null;
    }

    public String generateMfaToken(UserDetails userDetails){

        return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    public boolean isMfaTokenValid(String token, UserDetails userDetails){
        return false;
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return null;
    }

    public String extractType(String token){
        return null;
    }

    /**
     * usage of HmacSHA256 Algorithm
     *
     * @return SecretKey
     */
}
