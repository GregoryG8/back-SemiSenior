package com.e_commerce.back_SemiSenior.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@Data
public class Producto {
    String codigo;
    String nombre;

    @Positive
    BigDecimal precioUnitario;

    public Producto() {
    }
}

