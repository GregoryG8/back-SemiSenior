package com.e_commerce.back_SemiSenior.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClienteResponseDto {
    private Long id;
    private String nombre;
    private String email;
    private LocalDateTime fechaAlta;

    public ClienteResponseDto() {

    }
}
