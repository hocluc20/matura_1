package at.kaindorf.matura_learning_1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.security
 **/

@Service
public class JwtService {

    @Value("${token.secret}")
    private String jwtSecret;

    @Value("${token.duration}")
    private Duration duration;


    // ist gegeben
    private SecretKey getSigninKey(){
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // müssen wir machen
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .claim("type","access")
                .signWith(getSigninKey())
                .expiration(new Date(System.currentTimeMillis()+ duration.getSeconds() * 1000))
                .compact();
    }

    public String generateMfaToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .signWith(getSigninKey())
                .expiration(new Date(System.currentTimeMillis()*1000*60*15))
                .issuedAt(new Date())
                .claim("type", "mfa")
                .compact();
    }


    // müssen wir machen
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Claims claims = extractClaims(token);
        String username = userDetails.getUsername();
        Date actualDate = new Date();

        return username.equals(claims.getSubject()) && actualDate.before(claims.getExpiration()) && extractType(token).equals("access");
    }

    // müssen wir machen
    public boolean isMfaTokenValid(String mfaToken, UserDetails userDetails){
        Claims claims = extractClaims(mfaToken);
        String username = claims.getSubject();
        Date claimsdate = claims.getExpiration();
        String type = extractType(mfaToken);

        return type.equals("mfa") && username.equals(userDetails.getUsername()) && claimsdate.after(new Date());
    }

    // ist gegeben
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // müssen wir machen
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    public String extractType(String token){
        return extractClaims(token).get("type", String.class);
    }

}
