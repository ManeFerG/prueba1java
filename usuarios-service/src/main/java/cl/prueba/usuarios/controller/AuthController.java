package cl.prueba.usuarios.controller;

import cl.prueba.usuarios.dto.AuthRequest;
import cl.prueba.usuarios.dto.AuthResponse;
import cl.prueba.usuarios.dto.RegisterRequest;
import cl.prueba.usuarios.model.Usuario;
import cl.prueba.usuarios.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario register(@RequestBody RegisterRequest request) {
        return authService.registrar(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
