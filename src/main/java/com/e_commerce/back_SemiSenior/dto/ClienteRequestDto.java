package com.e_commerce.back_SemiSenior.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequestDto {

    @NotBlank(message="Nombre requerido")
    private String nombre;

    @Email(message="Email no válido")
    private String email;

    public ClienteRequestDto() {
    }
}
