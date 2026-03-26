package com.e_commerce.back_SemiSenior.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
@AllArgsConstructor
public class Producto {

    String codigo;

    String nombre;

    @Positive
    BigDecimal precioUnitario;

    public Producto() {
    }
}

