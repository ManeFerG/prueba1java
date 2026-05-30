package cl.prueba.usuarios.dto;

public class AuthResponse {
    private String token;
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String rol;

    public AuthResponse(String token, Long idUsuario, String nombre, String correo, String rol) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public Long getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
}
