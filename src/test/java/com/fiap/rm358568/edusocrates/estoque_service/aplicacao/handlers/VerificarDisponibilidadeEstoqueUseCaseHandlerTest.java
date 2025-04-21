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
class VerificarDisponibilidadeEstoqueUseCaseHandlerTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private VerificarDisponibilidadeEstoqueUseCaseHandler verificarDisponibilidadeEstoqueUseCaseHandler;

    @Test
    void verificarDisponibilidade_deveRetornarTrue_quandoQuantidadeEstiverDisponivel() {
        // Arrange
        String sku = "SKU123";
        int quantidade = 5;
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 10);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueMock));

        // Act
        boolean disponivel = verificarDisponibilidadeEstoqueUseCaseHandler.verificarDisponibilidade(sku, quantidade);

        // Assert
        assertTrue(disponivel);
        verify(estoqueGateway).buscarPorSku(sku);
    }

    @Test
    void verificarDisponibilidade_deveRetornarFalse_quandoQuantidadeNaoEstiverDisponivel() {
        // Arrange
        String sku = "SKU123";
        int quantidade = 15;
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 10);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueMock));

        // Act
        boolean disponivel = verificarDisponibilidadeEstoqueUseCaseHandler.verificarDisponibilidade(sku, quantidade);

        // Assert
        assertFalse(disponivel);
        verify(estoqueGateway).buscarPorSku(sku);
    }

    @Test
    void verificarDisponibilidade_deveLancarExcecao_quandoEstoqueNaoForEncontrado() {
        // Arrange
        String sku = "SKU123";
        int quantidade = 5;

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        // Act & Assert
        EstoqueNotFoundException exception = assertThrows(EstoqueNotFoundException.class, () ->
                verificarDisponibilidadeEstoqueUseCaseHandler.verificarDisponibilidade(sku, quantidade));

        assertEquals("SKU não encontrado!", exception.getMessage());
        verify(estoqueGateway).buscarPorSku(sku);
    }
}
