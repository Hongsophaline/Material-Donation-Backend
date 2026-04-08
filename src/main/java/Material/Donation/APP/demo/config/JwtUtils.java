package Material.Donation.APP.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    // IMPORTANT: In a real app, move this to application.properties
    private final String secret = "YourSuperSecretKeyForMaterialDonation2026!!!_MustBeLong";
    private final long expiration = 86400000; // 24 hours

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 1. Extract Email (Subject) from token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. Generic method to extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 3. Generate Token (Updated to modern JJWT 0.12+ syntax)
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // Changed from setSubject
                .issuedAt(new Date(System.currentTimeMillis())) // Changed from setIssuedAt
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Changed from setExpiration
                .signWith(getSigningKey()) // Algorithm is auto-detected
                .compact();
    }

    // 4. Validate Token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}