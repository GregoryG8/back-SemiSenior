package com.e_commerce.back_SemiSenior.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LineaPedidoDto {

    private ProductoDto producto;

    @Positive
    private Integer cantidad;
}
