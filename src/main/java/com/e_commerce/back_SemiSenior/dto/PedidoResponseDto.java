package com.e_commerce.back_SemiSenior.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoResponseDto {
    private Long id;
    private String referencia;
    private Long clienteId;
    private String estado;
    private BigDecimal total;
    private List<LineaPedidoDto> lineas;
}
