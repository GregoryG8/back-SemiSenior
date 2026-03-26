package com.e_commerce.back_SemiSenior.service;

import com.e_commerce.back_SemiSenior.dto.ClienteRequestDto;
import com.e_commerce.back_SemiSenior.dto.ClienteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {

    ClienteResponseDto crear(ClienteRequestDto clienteRequestDto);
    ClienteResponseDto buscarPorId(Long idCliente);
    Page<ClienteResponseDto> buscarTodos(Pageable  pageable);
    ClienteResponseDto actualizar(Long id, ClienteRequestDto clienteRequestDto);
    void eliminar(Long idCliente);

}
