package cl.prueba.usuarios;

import cl.prueba.usuarios.model.Usuario;
import cl.prueba.usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UsuariosServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsuariosServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner crearUsuarioInicial(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByCorreo("admin@demo.cl").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNombre("Administrador Demo");
                usuario.setCorreo("admin@demo.cl");
                usuario.setPassword(passwordEncoder.encode("123456"));
                usuario.setRol("ADMIN");
                usuario.setActivo(true);
                usuarioRepository.save(usuario);
            }
        };
    }
}
