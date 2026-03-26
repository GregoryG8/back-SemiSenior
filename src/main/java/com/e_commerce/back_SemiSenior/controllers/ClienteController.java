package com.e_commerce.back_SemiSenior.controllers;

import com.e_commerce.back_SemiSenior.dto.ClienteRequestDto;
import com.e_commerce.back_SemiSenior.dto.ClienteResponseDto;
import com.e_commerce.back_SemiSenior.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("")
    public ClienteResponseDto crearCliente(@RequestBody ClienteRequestDto clienteRequestDto) {
        return clienteService.crear(clienteRequestDto);
    }

    @GetMapping("/{id}")
    public ClienteResponseDto buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("")
    public Page<ClienteResponseDto> buscarTodosClientes(Pageable pageable) {
        return clienteService.buscarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ClienteResponseDto updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto clienteRequestDto) {
        return clienteService.actualizar(id, clienteRequestDto);
    }

}
