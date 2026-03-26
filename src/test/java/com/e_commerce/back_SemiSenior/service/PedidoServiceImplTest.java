package com.e_commerce.back_SemiSenior.service;

import com.e_commerce.back_SemiSenior.domain.Cliente;
import com.e_commerce.back_SemiSenior.domain.Estado;
import com.e_commerce.back_SemiSenior.domain.Pedido;
import com.e_commerce.back_SemiSenior.dto.LineaPedidoDto;
import com.e_commerce.back_SemiSenior.dto.PedidoRequestDto;
import com.e_commerce.back_SemiSenior.dto.PedidoResponseDto;
import com.e_commerce.back_SemiSenior.repository.ClienteRepository;
import com.e_commerce.back_SemiSenior.repository.LineaPedidoRepository;
import com.e_commerce.back_SemiSenior.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private LineaPedidoRepository lineaPedidoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Test
    void cambiarEstado_transicionPermitida_debeActualizarEstado() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Pedido pedido = new Pedido();
        pedido.setId(10L);
        pedido.setCliente(cliente);
        pedido.setEstado(Estado.CREADO);

        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PedidoResponseDto response = pedidoService.cambiarEstado(10L, "CONFIRMADO");

        assertThat(response.getEstado()).isEqualTo("CONFIRMADO");
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void cambiarEstado_transicionNoPermitida_debeLanzarError() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Pedido pedido = new Pedido();
        pedido.setId(10L);
        pedido.setCliente(cliente);
        pedido.setEstado(Estado.CREADO);

        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedido));

        assertThatThrownBy(() -> pedidoService.cambiarEstado(10L, "ENTREGADO"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Transición no permitida desde CREADO");

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void crear_debeCalcularTotalCorrectamente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        LineaPedidoDto linea1 = new LineaPedidoDto();
        linea1.setCodigo("SKU-1");
        linea1.setNombre("Teclado");
        linea1.setPrecio(new BigDecimal("10.50"));
        linea1.setCantidad(2);

        LineaPedidoDto linea2 = new LineaPedidoDto();
        linea2.setCodigo("SKU-2");
        linea2.setNombre("Mouse");
        linea2.setPrecio(new BigDecimal("5.00"));
        linea2.setCantidad(3);

        PedidoRequestDto request = new PedidoRequestDto();
        request.setClienteId(1L);
        request.setPedidos(List.of(linea1, linea2));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido toSave = invocation.getArgument(0);
            toSave.setId(99L);
            return toSave;
        });

        PedidoResponseDto response = pedidoService.crear(request);

        assertThat(response.getId()).isEqualTo(99L);
        assertThat(response.getEstado()).isEqualTo("CREADO");
        assertThat(response.getTotal()).isEqualByComparingTo(new BigDecimal("36.00"));
        assertThat(response.getLineas()).hasSize(2);
    }
}

