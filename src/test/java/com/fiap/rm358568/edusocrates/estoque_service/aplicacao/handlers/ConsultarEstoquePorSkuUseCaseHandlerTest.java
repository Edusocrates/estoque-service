package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;

import com.fiap.rm358568.edusocrates.estoque_service.API.exceptions.EstoqueNotFoundException;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstoquePorSkuUseCaseHandlerTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private ConsultarEstoquePorSkuUseCaseHandler consultarEstoquePorSkuUseCaseHandler;

    @Test
    void consultarPorSku_deveRetornarEstoque_quandoEstoqueExistir() {
        // Arrange
        String sku = "SKU123";
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 10);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueMock));

        // Act
        Estoque resultado = consultarEstoquePorSkuUseCaseHandler.consultarPorSku(sku);

        // Assert
        assertEquals(sku, resultado.getSku());
        assertEquals(10, resultado.getQuantidadeDisponivel());
        verify(estoqueGateway).buscarPorSku(sku);
    }

    @Test
    void consultarPorSku_deveLancarExcecao_quandoEstoqueNaoExistir() {
        // Arrange
        String sku = "SKU123";

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        // Act & Assert
        EstoqueNotFoundException exception = assertThrows(EstoqueNotFoundException.class, () ->
                consultarEstoquePorSkuUseCaseHandler.consultarPorSku(sku));

        assertEquals("Estoque não encontrado para o SKU: " + sku, exception.getMessage());
        verify(estoqueGateway).buscarPorSku(sku);
    }
}