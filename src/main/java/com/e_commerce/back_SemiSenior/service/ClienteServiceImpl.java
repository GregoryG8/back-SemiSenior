package com.e_commerce.back_SemiSenior.service;

import com.e_commerce.back_SemiSenior.domain.Cliente;
import com.e_commerce.back_SemiSenior.dto.ClienteRequestDto;
import com.e_commerce.back_SemiSenior.dto.ClienteResponseDto;
import com.e_commerce.back_SemiSenior.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Page<ClienteResponseDto> buscarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(cliente -> new ClienteResponseDto(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getFechaAlta()));
    }

    @Override
    public ClienteResponseDto actualizar(Long id, ClienteRequestDto clienteRequestDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));

        if (!cliente.getEmail().equals(clienteRequestDto.getEmail())
                && clienteRepository.existsByEmail(clienteRequestDto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        cliente.setNombre(clienteRequestDto.getNombre());
        cliente.setEmail(clienteRequestDto.getEmail());
        Cliente savedCliente = clienteRepository.save(cliente);

        return new ClienteResponseDto(savedCliente.getId(),
                savedCliente.getNombre(),
                savedCliente.getEmail(),
                savedCliente.getFechaAlta());
    }

    @Override
    public void eliminar(Long idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + idCliente));

        if (!clienteRepository.existsById(idCliente)) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + idCliente);
        }

        if (!cliente.getPedidos().isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el cliente con pedidos asociados");
        }

        clienteRepository.deleteById(idCliente);
    }

    @Override
    public ClienteResponseDto crear(ClienteRequestDto clienteRequestDto) {
        if (clienteRepository.existsByEmail(clienteRequestDto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRequestDto.getNombre());
        cliente.setEmail(clienteRequestDto.getEmail());
        Cliente savedCliente = clienteRepository.save(cliente);

        return new ClienteResponseDto(savedCliente.getId(),
                savedCliente.getNombre(),
                savedCliente.getEmail(),
                savedCliente.getFechaAlta());
    }

    @Override
    public ClienteResponseDto buscarPorId(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .map(cliente -> new ClienteResponseDto(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getFechaAlta()))
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + idCliente));
    }
}
