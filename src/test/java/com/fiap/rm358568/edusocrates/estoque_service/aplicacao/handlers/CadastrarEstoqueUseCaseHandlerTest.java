package com.fiap.rm358568.edusocrates.estoque_service.aplicacao.handlers;

import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarEstoqueUseCaseHandlerTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private CadastrarEstoqueUseCaseHandler cadastrarEstoqueUseCaseHandler;

    @Test
    void cadastrar_deveSalvarEstoque_quandoRequestForValida() {
        // Arrange
        CriarEstoqueRequest request = new CriarEstoqueRequest("SKU123", 10);
        Estoque estoqueMock = Estoque.builder()
                .sku(request.sku())
                .quantidadeDisponivel(request.quantidadeDisponivel())
                .build();

        when(estoqueGateway.salvar(any(Estoque.class))).thenReturn(estoqueMock);

        // Act
        Estoque resultado = cadastrarEstoqueUseCaseHandler.cadastrar(request);

        // Assert
        assertEquals(request.sku(), resultado.getSku());
        assertEquals(request.quantidadeDisponivel(), resultado.getQuantidadeDisponivel());
        verify(estoqueGateway).salvar(any(Estoque.class));
    }
}