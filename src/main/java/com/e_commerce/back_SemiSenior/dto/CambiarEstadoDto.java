package com.e_commerce.back_SemiSenior.dto;

import com.e_commerce.back_SemiSenior.domain.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Payload para actualizar el estado de un pedido")
public class CambiarEstadoDto {
    @NotNull(message = "El estado no puede ser nulo")
    @Schema(description = "Nuevo estado del pedido", example = "PAGADO")
    private Estado estado;
}
