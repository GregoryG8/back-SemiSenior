package com.e_commerce.back_SemiSenior.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Linea de pedido con producto, precio y cantidad")
public class LineaPedidoDto {
    @NotBlank
    @Schema(description = "Codigo del producto", example = "SKU-001")
    private String codigo;

    @NotBlank
    @Schema(description = "Nombre del producto", example = "Teclado mecanico")
    private String nombre;

    @NotNull
    @Schema(description = "Precio unitario de la linea", example = "55.99")
    private BigDecimal precio;

    @NotNull
    @Min(1)
    @Schema(description = "Cantidad solicitada", example = "2", minimum = "1")
    private Integer cantidad;
}
