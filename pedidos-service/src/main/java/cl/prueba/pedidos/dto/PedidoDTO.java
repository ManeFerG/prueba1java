package cl.prueba.pedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoDTO {
    private Long id;
    private String descripcion;
    private String estado;
    private BigDecimal total;
    private LocalDateTime fechaCreacion;
    private Long usuarioId;
    private UsuarioDTO usuario;

    public PedidoDTO() {}

    public PedidoDTO(Long id, String descripcion, String estado, BigDecimal total, 
                     LocalDateTime fechaCreacion, Long usuarioId) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.total = total;
        this.fechaCreacion = fechaCreacion;
        this.usuarioId = usuarioId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }
}
