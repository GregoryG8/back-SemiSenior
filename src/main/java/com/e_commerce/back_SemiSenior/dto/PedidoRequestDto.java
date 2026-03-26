package com.e_commerce.back_SemiSenior.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un pedido")
public class PedidoRequestDto {
    @NotNull(message = "ClienteId requerido")
    @Schema(description = "ID del cliente propietario del pedido", example = "1")
    private Long clienteId;

    @ArraySchema(schema = @Schema(implementation = LineaPedidoDto.class),
            arraySchema = @Schema(description = "Lineas iniciales del pedido"))
    private List<LineaPedidoDto> pedidos;

    public PedidoRequestDto() {
    }
}
