package com.e_commerce.back_SemiSenior.controllers;

import com.e_commerce.back_SemiSenior.domain.Cliente;
import com.e_commerce.back_SemiSenior.domain.Estado;
import com.e_commerce.back_SemiSenior.domain.Pedido;
import com.e_commerce.back_SemiSenior.repository.ClienteRepository;
import com.e_commerce.back_SemiSenior.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class PedidoControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();

        cliente = new Cliente();
        cliente.setNombre("Cliente IT");
        cliente.setEmail("cliente.it@example.com");
        cliente = clienteRepository.save(cliente);
    }

    @Test
    void crearPedido_debePersistirYCalcularTotal() throws Exception {
        String payload = """
                {
                  "clienteId": %d,
                  "pedidos": [
                    {"codigo":"SKU-1","nombre":"Mouse","precio":20.00,"cantidad":2},
                    {"codigo":"SKU-2","nombre":"Pad","precio":10.00,"cantidad":3}
                  ]
                }
                """.formatted(cliente.getId());

        MvcResult result = mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CREADO"))
                .andExpect(jsonPath("$.total").value(70.0))
                .andExpect(jsonPath("$.lineas.length()").value(2))
                .andReturn();

        long pedidoId = extraerId(result.getResponse().getContentAsString());
        Pedido guardado = pedidoRepository.findById(pedidoId).orElseThrow();

        assertThat(guardado.getEstado()).isEqualTo(Estado.CREADO);
        assertThat(guardado.getTotal()).isEqualByComparingTo(new BigDecimal("70.00"));
    }

    @Test
    void cambiarEstado_debeActualizarPedido() throws Exception {
        String crearPayload = """
                {
                  "clienteId": %d,
                  "pedidos": [
                    {"codigo":"SKU-1","nombre":"Mouse","precio":15.00,"cantidad":1}
                  ]
                }
                """.formatted(cliente.getId());

        MvcResult creado = mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(crearPayload))
                .andExpect(status().isOk())
                .andReturn();

        long pedidoId = extraerId(creado.getResponse().getContentAsString());

        String patchPayload = """
                {"estado":"CONFIRMADO"}
                """;

        mockMvc.perform(patch("/api/pedidos/{id}/estado", pedidoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CONFIRMADO"));

        Pedido actualizado = pedidoRepository.findById(pedidoId).orElseThrow();
        assertThat(actualizado.getEstado()).isEqualTo(Estado.CONFIRMADO);
    }

    private long extraerId(String json) {
        Pattern pattern = Pattern.compile("\\\"id\\\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IllegalStateException("No se encontro el id en la respuesta: " + json);
        }
        return Long.parseLong(matcher.group(1));
    }
}





