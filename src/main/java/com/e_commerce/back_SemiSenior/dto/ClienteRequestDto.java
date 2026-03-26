package com.e_commerce.back_SemiSenior.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un cliente")
public class ClienteRequestDto {

    @NotBlank(message="Nombre requerido")
    @Schema(description = "Nombre completo del cliente", example = "Ana Martinez")
    private String nombre;

    @Email(message="Email no válido")
    @Schema(description = "Correo electronico del cliente", example = "ana.martinez@email.com")
    private String email;

    public ClienteRequestDto() {
    }
}
