package cl.prueba.pedidos.service;

import cl.prueba.pedidos.model.Pedido;
import cl.prueba.pedidos.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listar(Long usuarioId) {
        if (usuarioId != null) {
            return pedidoRepository.findByUsuarioId(usuarioId);
        }
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    public Pedido crear(Pedido pedido) {
        if (pedido.getEstado() == null || pedido.getEstado().isBlank()) {
            pedido.setEstado("PENDIENTE");
        }
        if (pedido.getFechaCreacion() == null) {
            pedido.setFechaCreacion(LocalDateTime.now());
        }
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizar(Long id, Pedido datos) {
        Pedido pedido = buscarPorId(id);
        pedido.setDescripcion(datos.getDescripcion());
        pedido.setEstado(datos.getEstado());
        pedido.setTotal(datos.getTotal());
        if (datos.getUsuario() != null) {
            pedido.setUsuario(datos.getUsuario());
        }
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> buscarPorUsuario(Long usuarioId) {
        // Este método se utiliza si necesitas buscar por ID de usuario
        // En la BD se hace con el JOIN automático
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public void eliminar(Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
    }
}
