package com.e_commerce.back_SemiSenior.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDto {
    @NotBlank(message="ClienteId requerido")
    private Long clienteId;
    private List<LineaPedidoDto> pedidos;

    public PedidoRequestDto() {
    }
}
