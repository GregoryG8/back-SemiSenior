package com.e_commerce.back_SemiSenior.controllers;

import com.e_commerce.back_SemiSenior.dto.*;
import com.e_commerce.back_SemiSenior.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operaciones para gestion de pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("")
    @Operation(summary = "Crear pedido", description = "Registra un nuevo pedido para un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado correctamente",
                    content = @Content(schema = @Schema(implementation = PedidoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos del pedido", content = @Content)
    })
    public PedidoResponseDto crearPedido(@Valid @RequestBody PedidoRequestDto pedidoRequestDto) {
        return pedidoService.crear(pedidoRequestDto);
    }

    @PostMapping("/{idPedido}")
    @Operation(summary = "Agregar linea a pedido", description = "Crea una nueva linea dentro de un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Linea agregada correctamente",
                    content = @Content(schema = @Schema(implementation = PedidoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content)
    })
    public PedidoResponseDto crearLineaPedido(
            @Parameter(description = "ID del pedido", example = "10") @PathVariable Long idPedido,
            @Valid @RequestBody LineaPedidoDto lineaPedidoDto) {
        return pedidoService.crearLineaPedido(idPedido, lineaPedidoDto);
    }

    @GetMapping("/{idCliente}")
    @Operation(summary = "Listar pedidos por cliente", description = "Obtiene pedidos paginados para un cliente")
    @ApiResponse(responseCode = "200", description = "Pedidos obtenidos correctamente")
    public Page<PedidoResponseDto> obtenerPedidoPorId(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long idCliente,
            Pageable pageable) {
        return pedidoService.buscarPorIdCliente(idCliente, pageable);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de pedido", description = "Actualiza el estado actual de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado",
                    content = @Content(schema = @Schema(implementation = PedidoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content)
    })
    public PedidoResponseDto cambiarEstado(
            @Parameter(description = "ID del pedido", example = "10") @PathVariable Long id,
            @Valid @RequestBody CambiarEstadoDto estado) {
        return pedidoService.cambiarEstado(id, estado.getEstado().name());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza los datos generales de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado",
                    content = @Content(schema = @Schema(implementation = PedidoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content)
    })
    public PedidoResponseDto actualizarPedido(
            @Parameter(description = "ID del pedido", example = "10") @PathVariable Long id,
            @Valid @RequestBody PedidoRequestDto pedidoRequestDto) {
        return pedidoService.actualizar(id, pedidoRequestDto);
    }

//    @GetMapping("")
//    public Page<PedidoResponseDto> buscarConFiltros(
//            @RequestParam(required = false) String estado,
//            @RequestParam(required = false) String fechaDesde,
//            @RequestParam(required = false) String fechaHasta,
//            @RequestParam(required = false) Long clienteId,
//            Pageable pageable
//    ) {
//        return pedidoService.buscarConFiltros( fechaDesde != null ? LocalDateTime.parse(fechaDesde) : null, fechaHasta != null ? LocalDateTime.parse(fechaHasta) : null, clienteId, pageable);
//    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido por identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminarPedido(
            @Parameter(description = "ID del pedido", example = "10") @PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
