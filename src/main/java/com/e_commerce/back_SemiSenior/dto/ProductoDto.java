package com.e_commerce.back_SemiSenior.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Schema(description = "Datos de producto utilizados en pedidos")
public class ProductoDto {

    @Schema(description = "Codigo del producto", example = "SKU-001")
    String codigo;

    @Schema(description = "Nombre del producto", example = "Teclado mecanico")
    String nombre;

    @Positive
    @Schema(description = "Precio unitario del producto", example = "55.99")
    BigDecimal precioUnitario;
}
