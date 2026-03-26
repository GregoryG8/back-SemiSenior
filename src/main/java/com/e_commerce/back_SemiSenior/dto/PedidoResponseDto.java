package com.e_commerce.back_SemiSenior.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "Representa un pedido en las respuestas de la API")
public class PedidoResponseDto {
    @Schema(description = "ID del pedido", example = "10")
    private Long id;
    @Schema(description = "Referencia unica del pedido", example = "PED-2026-00010")
    private String referencia;
    @Schema(description = "ID del cliente propietario", example = "1")
    private Long clienteId;
    @Schema(description = "Estado actual del pedido", example = "PENDIENTE")
    private String estado;
    @Schema(description = "Total del pedido", example = "150.75")
    private BigDecimal total;

    @ArraySchema(schema = @Schema(implementation = LineaPedidoDto.class),
            arraySchema = @Schema(description = "Lineas del pedido"))
    private List<LineaPedidoDto> lineas;
}
