package orbezo.auth_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ USAR LA MISMA CLAVE SECRETA QUE EN USUARIO-SERVICE
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    public String generateToken(Long userId, String username, String rol) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            return !extractClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRol(String token) {
        return extractClaims(token).get("rol", String.class);
    }

    public Long getExpiration() {
        return expiration;
    }
}