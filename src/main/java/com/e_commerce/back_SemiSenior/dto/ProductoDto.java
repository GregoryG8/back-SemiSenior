package com.e_commerce.back_SemiSenior.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoDto {

    private  String codigo;
    private String nombre;

    @Positive
    private BigDecimal precioUnitario;
}
