package com.e_commerce.back_SemiSenior.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;

@Entity
@Table(name = "linea_pedido")
@Data
public class LineaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Embedded
    private Producto producto;

    @Positive
    private int cantidad;

    private BigDecimal subtotal = BigDecimal.ZERO;

    public void recalcularSubtotal() {
        if (producto != null && producto.getPrecioUnitario() != null) {
            this.subtotal = producto.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(cantidad));
        }
    }
}
