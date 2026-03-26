package com.e_commerce.back_SemiSenior.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa un cliente en las respuestas de la API")
public class ClienteResponseDto {
    @Schema(description = "Identificador del cliente", example = "1")
    private Long id;
    @Schema(description = "Nombre del cliente", example = "Ana Martinez")
    private String nombre;
    @Schema(description = "Email del cliente", example = "ana.martinez@email.com")
    private String email;
    @Schema(description = "Fecha y hora de alta del cliente", example = "2026-03-26T14:30:00")
    private LocalDateTime fechaAlta;

}
