package com.e_commerce.back_SemiSenior.service;

import com.e_commerce.back_SemiSenior.domain.Estado;
import com.e_commerce.back_SemiSenior.dto.LineaPedidoDto;
import com.e_commerce.back_SemiSenior.dto.PedidoRequestDto;
import com.e_commerce.back_SemiSenior.dto.PedidoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface PedidoService {

    PedidoResponseDto crear(PedidoRequestDto pedidoRequestDto);
    PedidoResponseDto crearLineaPedido(Long id, LineaPedidoDto lineaPedidoDto);
    PedidoResponseDto actualizar(Long id, PedidoRequestDto pedidoRequestDto);
    void eliminar(Long id);

    PedidoResponseDto obtenerPorId(Long id);
    Page<PedidoResponseDto> buscarPorIdCliente(Long idCliente, Pageable pageable);
//    Page<PedidoResponseDto> buscarConFiltros(
//            long estado,
//            LocalDateTime fechaDesde,
//            LocalDateTime fechaHasta,
//            Long clienteId,
//            Pageable pageable
//    );
    PedidoResponseDto cambiarEstado(Long id, String estado);

}
