package uz.ccrew.flightmanagement.security.jwt;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.security.Key;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class JWTServiceTest {
    @Value("${security.token.access.secret-key}")
    private String ACCESS_TOKEN_SECRET_KEY;
    @Autowired
    private JWTService jwtService;

    @Test
    void testGenerateAccessToken() {
        String username = "Azimjon";
        String token = jwtService.generateAccessToken(username);

        assertNotNull(token);
        assertFalse(jwtService.isTokenExpired(token));

        String extractedUsername = jwtService.extractAccessTokenLogin(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testGenerateRefreshToken() {
        String username = "Azimjon";
        String token = jwtService.generateRefreshToken(username);

        assertNotNull(token);
        assertFalse(jwtService.isTokenExpired(token));

        String extractedUsername = jwtService.extractRefreshTokenLogin(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testIsTokenExpired() {
        String username = "Azimjon";
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY));
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertTrue(jwtService.isTokenExpired(token));
    }

    @Test
    void testGetGeneratedTime() {
        String username = "Azimjon";
        String token = jwtService.generateAccessToken(username);

        LocalDateTime generatedTime = jwtService.getGeneratedTime(token);
        assertNotNull(generatedTime);
    }

    @Test
    void testGetTokenExpiredMessage() {
        String username = "Azimjon";
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY));
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String message = jwtService.getTokenExpiredMessage(token);
        assertTrue(message.contains("JWT expired at"));
    }

    @Test
    void testExtractAccessTokenLogin() {
        String username = "Azimjon";
        String token = jwtService.generateAccessToken(username);

        String extractedUsername = jwtService.extractAccessTokenLogin(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractRefreshTokenLogin() {
        String username = "Azimjon";
        String token = jwtService.generateRefreshToken(username);

        String extractedUsername = jwtService.extractRefreshTokenLogin(token);
        assertEquals(username, extractedUsername);
    }
}
