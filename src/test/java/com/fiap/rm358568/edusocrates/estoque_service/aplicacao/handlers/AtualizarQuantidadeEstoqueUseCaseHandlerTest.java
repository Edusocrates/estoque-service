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
class AtualizarQuantidadeEstoqueUseCaseHandlerTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private AtualizarQuantidadeEstoqueUseCaseHandler atualizarQuantidadeEstoqueUseCaseHandler;

    @Test
    void atualizarQuantidadeEstoque_deveAdicionarQuantidade_quandoQuantidadeForPositiva() {
        // Arrange
        String sku = "SKU123";
        int quantidade = 10;
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 5);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueMock));
        when(estoqueGateway.salvar(estoqueMock)).thenReturn(estoqueMock);

        // Act
        Estoque resultado = atualizarQuantidadeEstoqueUseCaseHandler.atualizarQuantidadeEstoque(sku, quantidade);

        // Assert
        assertEquals(15, resultado.getQuantidadeDisponivel());
        verify(estoqueGateway).buscarPorSku(sku);
        verify(estoqueGateway).salvar(estoqueMock);
    }

    @Test
    void atualizarQuantidadeEstoque_deveRemoverQuantidade_quandoQuantidadeForNegativa() {
        // Arrange
        String sku = "SKU123";
        int quantidade = -5;
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 10);

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueMock));
        when(estoqueGateway.salvar(estoqueMock)).thenReturn(estoqueMock);

        // Act
        Estoque resultado = atualizarQuantidadeEstoqueUseCaseHandler.atualizarQuantidadeEstoque(sku, quantidade);

        // Assert
        assertEquals(5, resultado.getQuantidadeDisponivel());
        verify(estoqueGateway).buscarPorSku(sku);
        verify(estoqueGateway).salvar(estoqueMock);
    }

    @Test
    void atualizarQuantidadeEstoque_deveLancarExcecao_quandoEstoqueNaoForEncontrado() {
        // Arrange
        String sku = "SKU123";
        int quantidade = 10;

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        // Act & Assert
        EstoqueNotFoundException exception = assertThrows(EstoqueNotFoundException.class, () ->
                atualizarQuantidadeEstoqueUseCaseHandler.atualizarQuantidadeEstoque(sku, quantidade));

        assertEquals("Estoque não encontrado para SKU: " + sku, exception.getMessage());
        verify(estoqueGateway).buscarPorSku(sku);
        verify(estoqueGateway, never()).salvar(any());
    }
}
