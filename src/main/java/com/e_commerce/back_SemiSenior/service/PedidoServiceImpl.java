package com.e_commerce.back_SemiSenior.service;

import com.e_commerce.back_SemiSenior.domain.*;
import com.e_commerce.back_SemiSenior.dto.LineaPedidoDto;
import com.e_commerce.back_SemiSenior.dto.PedidoRequestDto;
import com.e_commerce.back_SemiSenior.dto.PedidoResponseDto;
import com.e_commerce.back_SemiSenior.repository.ClienteRepository;
import com.e_commerce.back_SemiSenior.repository.LineaPedidoRepository;
import com.e_commerce.back_SemiSenior.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    private LineaPedido toLineaPedido(LineaPedidoDto lineaPedidoDto, Pedido pedido) {
        LineaPedido linea = new LineaPedido();
        linea.setPedido(pedido);
        linea.setProducto(new Producto(
                lineaPedidoDto.getCodigo(),
                lineaPedidoDto.getNombre(),
                lineaPedidoDto.getPrecio()
        ));
        linea.setCantidad(lineaPedidoDto.getCantidad());
        linea.recalcularSubtotal();
        return linea;
    }

    @Override
    public PedidoResponseDto crear(PedidoRequestDto pedidoRequestDto) {
         Cliente cliente = clienteRepository.findById(pedidoRequestDto.getClienteId())
                 .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + pedidoRequestDto.getClienteId()));

         Pedido pedido = new Pedido();
         pedido.setCliente(cliente);
         pedido.setReferencia(generarReferencia());
         pedido.setFecha(LocalDateTime.now());
         pedido.setEstado(Estado.CREADO);

         pedidoRequestDto.getPedidos().forEach(dto -> {  // ← getLineas()
             LineaPedido lineaPedido = toLineaPedido(dto, pedido);
             pedido.getLineasPedido().add(lineaPedido);       // ← getLineas()
         });

         pedido.setTotal(calcularTotal(pedido.getLineasPedido()));
         Pedido guardado = pedidoRepository.save(pedido);

         return toResponseDto(guardado);

    }

    @Override
    @Transactional
    public PedidoResponseDto crearLineaPedido(Long idPedido, LineaPedidoDto lineaPedidoDto) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + idPedido));

        LineaPedido linea = new LineaPedido();
        linea.setPedido(pedido);
        linea.setProducto(new Producto(
                lineaPedidoDto.getCodigo(),
                lineaPedidoDto.getNombre(),
                lineaPedidoDto.getPrecio()
        ));
        linea.setCantidad(lineaPedidoDto.getCantidad());
        linea.recalcularSubtotal();

        pedido.getLineasPedido().add(linea);
        pedido.recalcularTotal();
        Pedido guardado = pedidoRepository.save(pedido);
        return toResponseDto(guardado);
    }

    @Override
    public PedidoResponseDto actualizar(Long id, PedidoRequestDto pedidoRequestDto) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del pedido es requerido para actualizar");
        }

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + id));

        if (pedido.getEstado() == Estado.CANCELADO || pedido.getEstado() == Estado.ENTREGADO) {
            throw new IllegalStateException("No se puede actualizar un pedido en estado " + pedido.getEstado());
        }

        if (pedidoRequestDto.getClienteId() != null && !pedidoRequestDto.getClienteId().equals(pedido.getCliente().getId())) {
            Cliente cliente = clienteRepository.findById(pedidoRequestDto.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + pedidoRequestDto.getClienteId()));
            pedido.setCliente(cliente);
        }

        pedido.getLineasPedido().clear();
        pedidoRequestDto.getPedidos().forEach(dto -> {
            LineaPedido lineaPedido = toLineaPedido(dto, pedido);
            pedido.getLineasPedido().add(lineaPedido);
        });

        pedido.recalcularTotal();

        Pedido actualizado = pedidoRepository.save(pedido);
        return toResponseDto(actualizado);
    }

    @Override
    public PedidoResponseDto obtenerPorId(Long id) {
         return null;
    }

    @Override
    public Page<PedidoResponseDto> buscarPorIdCliente(Long idCliente, Pageable pageable) {
        return pedidoRepository.findByClienteId(idCliente, pageable)
                .map(this::toResponseDto);
    }

//    @Override
//    public Page<PedidoResponseDto> buscarConFiltros(
//            String estadoStr,
//            LocalDateTime fechaDesde,
//            LocalDateTime fechaHasta,
//            Long clienteId,
//            Pageable pageable){
//
//        Estado estado = null;
//        if (estadoStr != null && !estadoStr.name().equals("")) {
//            estado = Estado.valueOf(estadoStr.name().toUpperCase());
//        }
//
//        return pedidoRepository.findByFilters(estado, fechaDesde, fechaHasta, clienteId, pageable)
//                .map(this::toResponseDto);
//    }

    @Override
    public void eliminar(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + idPedido));


        clienteRepository.deleteById(idPedido);
    }

    @Override
    public PedidoResponseDto cambiarEstado(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + id));

        Estado estadoActual = pedido.getEstado();
        Estado nuevoEstado = Estado.valueOf(estado.toUpperCase());

        validarTransicionEstado(estadoActual, nuevoEstado);

        pedido.setEstado(nuevoEstado);
        Pedido actualizado = pedidoRepository.save(pedido);

        return toResponseDto(actualizado);

    }

    private String generarReferencia() {
        return "ORD-" + LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS")
        );
    }

    private BigDecimal calcularTotal(java.util.List<LineaPedido> lineas) {
        return lineas.stream()
                .map(l -> l.getProducto().getPrecioUnitario().multiply(BigDecimal.valueOf(l.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PedidoResponseDto toResponseDto(Pedido pedido) {
        PedidoResponseDto dto = new PedidoResponseDto();
        dto.setId(pedido.getId());
        dto.setReferencia(pedido.getReferencia());
        dto.setClienteId(pedido.getCliente().getId());
        dto.setEstado(pedido.getEstado().name());
        dto.setTotal(pedido.getTotal());

        List<LineaPedidoDto> lineasDto = pedido.getLineasPedido().stream()
                .map(lp -> {
                    LineaPedidoDto l = new LineaPedidoDto();
                    l.setCodigo(lp.getProducto().getCodigo());
                    l.setNombre(lp.getProducto().getNombre());
                    l.setPrecio(lp.getProducto().getPrecioUnitario());
                    l.setCantidad(lp.getCantidad());
                    return l;
                }).toList();
        dto.setLineas(lineasDto);

        return dto;
    }

    private void validarTransicionEstado(Estado actual, Estado nuevo) {
        // No se puede salir de CANCELADO ni de ENTREGADO
        if (actual == Estado.CANCELADO && nuevo != Estado.CANCELADO) {
            throw new IllegalArgumentException("No se puede cambiar estado desde CANCELADO");
        }
        if (actual == Estado.ENTREGADO && nuevo != Estado.ENTREGADO) {
            throw new IllegalArgumentException("No se puede cambiar estado desde ENTREGADO");
        }

        switch (actual) {
            case CREADO -> {
                if (nuevo != Estado.CONFIRMADO && nuevo != Estado.CANCELADO) {
                    throw new IllegalArgumentException("Transición no permitida desde CREADO");
                }
            }
            case CONFIRMADO -> {
                if (nuevo != Estado.ENVIADO && nuevo != Estado.CANCELADO) {
                    throw new IllegalArgumentException("Transición no permitida desde CONFIRMADO");
                }
            }
            case ENVIADO -> {
                if (nuevo != Estado.ENTREGADO && nuevo != Estado.CANCELADO) {
                    throw new IllegalArgumentException("Transición no permitida desde ENVIADO");
                }
            }
        }
    }

}
