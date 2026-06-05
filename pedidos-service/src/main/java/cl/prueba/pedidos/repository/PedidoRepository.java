package cl.prueba.pedidos.repository;

import cl.prueba.pedidos.model.Pedido;
import cl.prueba.pedidos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(Usuario usuario);
    List<Pedido> findByUsuarioId(Long usuarioId);
}
