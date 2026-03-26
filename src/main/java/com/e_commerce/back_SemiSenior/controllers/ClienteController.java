package com.e_commerce.back_SemiSenior.controllers;

import com.e_commerce.back_SemiSenior.dto.ClienteRequestDto;
import com.e_commerce.back_SemiSenior.dto.ClienteResponseDto;
import com.e_commerce.back_SemiSenior.service.ClienteService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones para administracion de clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("")
    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado correctamente",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de cliente invalidos", content = @Content)
    })
    public ClienteResponseDto crearCliente(@Valid @RequestBody ClienteRequestDto clienteRequestDto) {
        return clienteService.crear(clienteRequestDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Obtiene un cliente por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    public ClienteResponseDto buscarClientePorId(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("")
    @Operation(summary = "Listar clientes", description = "Devuelve clientes paginados")
    @ApiResponse(responseCode = "200", description = "Listado de clientes obtenido")
    public Page<ClienteResponseDto> buscarTodosClientes(
            @Parameter(description = "Numero de pagina (inicia en 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por pagina", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza la informacion de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    public ClienteResponseDto updateCliente(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDto clienteRequestDto) {
        return clienteService.actualizar(id, clienteRequestDto);
    }

}
