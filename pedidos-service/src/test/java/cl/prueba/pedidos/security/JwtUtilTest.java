package cl.prueba.pedidos.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {
    @Test
    void validaTokenGeneradoConMismaLlaveSecreta() {
        String secret = "clave-super-secreta-de-prueba-para-firmar-jwt-2026";
        String token = Jwts.builder()
                .subject("admin@demo.cl")
                .claim("rol", "ADMIN")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        JwtUtil jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", secret);

        assertTrue(jwtUtil.tokenValido(token));
        assertEquals("admin@demo.cl", jwtUtil.obtenerClaims(token).getSubject());
    }
}
