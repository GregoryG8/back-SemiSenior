package com.e_commerce.back_SemiSenior.dto;

import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductoDto {

    String codigo;
    String nombre;

    @Positive
    BigDecimal precioUnitario;
}
