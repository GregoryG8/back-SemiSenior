package com.e_commerce.back_SemiSenior.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CrearPedidoDto {

    @NotNull
    private Long clienteId;

    List<LineaPedidoDto>  lineas;
}
