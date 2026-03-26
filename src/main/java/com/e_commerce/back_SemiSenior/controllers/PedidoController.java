package com.e_commerce.back_SemiSenior.controllers;

import com.e_commerce.back_SemiSenior.domain.Estado;
import com.e_commerce.back_SemiSenior.dto.*;
import com.e_commerce.back_SemiSenior.service.ClienteService;
import com.e_commerce.back_SemiSenior.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("")
    public PedidoResponseDto crearPedido(@RequestBody  PedidoRequestDto pedidoRequestDto) {
        return pedidoService.crear(pedidoRequestDto);
    }

    @PostMapping("/{idPedido}")
    public PedidoResponseDto crearLineaPedido(@PathVariable Long idPedido, @RequestBody LineaPedidoDto lineaPedidoDto) {
        return pedidoService.crearLineaPedido(idPedido, lineaPedidoDto);
    }

    @GetMapping("/{idCliente}")
    public Page<PedidoResponseDto> obtenerPedidoPorId(@PathVariable Long idCliente, Pageable pageable) {
        return pedidoService.buscarPorIdCliente(idCliente, pageable);
    }

    @PatchMapping("/{id}/estado")
    public PedidoResponseDto cambiarEstado(@PathVariable Long id, @RequestBody CambiarEstadoDto estado) {
        return pedidoService.cambiarEstado(id, estado.getEstado().name());
    }

    @PutMapping("/{id}")
    public PedidoResponseDto actualizarPedido(@PathVariable Long id, @RequestBody PedidoRequestDto pedidoRequestDto) {
        return pedidoService.actualizar(id, pedidoRequestDto);
    }


}
