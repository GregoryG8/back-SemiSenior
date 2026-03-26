package com.e_commerce.back_SemiSenior.repository;

import com.e_commerce.back_SemiSenior.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByClienteId(Long id, Pageable pageable);

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.lineasPedido WHERE" +
        "(:estado IS NULL OR p.estado = :estado) AND " +
        "(:fechaDesde IS NULL OR p.fecha >= :fechaDesde) AND " +
        "(:fechaHasta IS NULL OR p.fecha <= :fechaHasta) AND " +
        "(:clienteId IS NULL OR p.cliente.id = :clienteId)")
    Page<Pedido> findByFilters(String estado, LocalDateTime fechaDesde, LocalDateTime fechaHasta, Long clienteId, Pageable pageable);

    Optional<Pedido> findByReferencia(String referencia);
    boolean existsByReferencia(String referencia);
}
