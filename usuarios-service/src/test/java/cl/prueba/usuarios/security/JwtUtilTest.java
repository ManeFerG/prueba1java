package cl.prueba.usuarios.security;

import cl.prueba.usuarios.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {
    @Test
    void generaYValidaToken() {
        JwtUtil jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "clave-super-secreta-de-prueba-para-firmar-jwt-2026");
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 3600000L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("admin@demo.cl");
        usuario.setRol("ADMIN");

        String token = jwtUtil.generarToken(usuario);

        assertTrue(jwtUtil.tokenValido(token));
        assertEquals("admin@demo.cl", jwtUtil.obtenerClaims(token).getSubject());
        assertEquals("ADMIN", jwtUtil.obtenerClaims(token).get("rol", String.class));
    }
}
