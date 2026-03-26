package com.e_commerce.back_SemiSenior.service;

import com.e_commerce.back_SemiSenior.dto.LineaPedidoDto;
import com.e_commerce.back_SemiSenior.dto.PedidoRequestDto;
import com.e_commerce.back_SemiSenior.dto.PedidoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PedidoService {

    PedidoResponseDto crear(PedidoRequestDto pedidoRequestDto);
    PedidoResponseDto crearLineaPedido(Long id, LineaPedidoDto lineaPedidoDto);
    PedidoResponseDto actualizar(Long id, PedidoRequestDto pedidoRequestDto);
    PedidoResponseDto obtenerPorId(Long id);
    Page<PedidoResponseDto> buscarPorIdCliente(Long idCliente, Pageable pageable);
    Page<PedidoResponseDto> obtenerTodos(Pageable pageable);
    PedidoResponseDto cambiarEstado(Long id, String estado);

}
