package com.e_commerce.back_SemiSenior.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineaPedidoDto {
    @NotBlank
    String codigo;

    @NotBlank
    String nombre;

    @NotNull
    BigDecimal precio;

    @NotNull
    @Min(1)
    Integer cantidad;
}
