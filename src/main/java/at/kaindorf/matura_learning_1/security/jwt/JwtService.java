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

@Service
public class JwtService {
    @Value("${token.secret}")
    private String jwtSecret;

    @Value("${token.duration}")
    private Duration duration;

    private SecretKey getSigningKey(){
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + duration.getSeconds() * 1000))
                .claim("type", "access")
                .signWith(getSigningKey())
                .compact();
    }

    public String generateMfaToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() * 1000 * 60 * 15))
                .claim("type", "mfa")
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        Claims claims = extractClaims(token);
        String username = userDetails.getUsername();
        Date actualDate = new Date();
        String type = extractType(token);
        return username.equals(claims.getSubject()) && actualDate.before(claims.getExpiration()) && type.equals("access");
    }

    public boolean isMfaTokenValid(String token, UserDetails userDetails){
        Claims claims = extractClaims(token);
        String username = extractUsername(token);
        Date actualDate = new Date();
        String type = extractType(token);
        return username.equals(claims.getSubject()) && actualDate.before(claims.getExpiration()) && type.equals("mfa");
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractType(String token){
        return  extractClaims(token).get("type", String.class);
    }

    /**
     * usage of HmacSHA256 Algorithm
     *
     * @return SecretKey
     */
}
