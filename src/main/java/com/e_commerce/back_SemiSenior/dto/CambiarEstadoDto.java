package com.e_commerce.back_SemiSenior.dto;

import com.e_commerce.back_SemiSenior.domain.Estado;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarEstadoDto {
    @NotNull(message = "El estado no puede ser nulo")
    private Estado estado;
}
