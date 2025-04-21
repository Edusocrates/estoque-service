package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;


import com.fiap.rm358568.edusocrates.estoque_service.API.exceptions.EstoqueNotFoundException;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.producers.EstoqueProducer;
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
class CreditarEstoqueUseCaseHandlerTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @Mock
    private EstoqueProducer estoqueProducer;

    @InjectMocks
    private CreditarEstoqueUseCaseHandler creditarEstoqueUseCaseHandler;

    @Test
    void executar_deveAdicionarQuantidade_quandoEstoqueExistir() {
        // Arrange
        String sku = "SKU123";
        int quantidade = 10;
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 5);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueMock));
        when(estoqueGateway.salvar(estoqueMock)).thenReturn(estoqueMock);

        // Act
        Estoque resultado = creditarEstoqueUseCaseHandler.executar(sku, quantidade);

        // Assert
        assertEquals(15, resultado.getQuantidadeDisponivel());
        verify(estoqueGateway).buscarPorSku(sku);
        verify(estoqueGateway).salvar(estoqueMock);
        verify(estoqueProducer).enviarAtualizacaoEstoque(estoqueMock);
    }

    @Test
    void executar_deveLancarExcecao_quandoEstoqueNaoExistir() {
        // Arrange
        String sku = "SKU123";
        int quantidade = 10;

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        // Act & Assert
        EstoqueNotFoundException exception = assertThrows(EstoqueNotFoundException.class, () ->
                creditarEstoqueUseCaseHandler.executar(sku, quantidade));

        assertEquals("Estoque não encontrado para SKU: " + sku, exception.getMessage());
        verify(estoqueGateway).buscarPorSku(sku);
        verify(estoqueGateway, never()).salvar(any());
        verify(estoqueProducer, never()).enviarAtualizacaoEstoque(any());
    }
}
