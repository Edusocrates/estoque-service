package com.fiap.rm358568.edusocrates.estoque_service.API.controllers;


import com.fiap.rm358568.edusocrates.estoque_service.API.mappers.EstoqueMapper;
import com.fiap.rm358568.edusocrates.estoque_service.API.requests.AtualizarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.API.responses.EstoqueResponse;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.*;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.mappers.EstoqueEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstoqueControllerTest {

    @Mock
    private CadastrarEstoqueUseCase cadastrarEstoqueHandler;

    @Mock
    private ConsultarEstoquePorSkuUseCase consultarEstoqueHandler;

    @Mock
    private CreditarEstoqueUseCase creditarEstoqueHandler;

    @Mock
    private DebitarEstoqueUseCase debitarEstoqueHandler;

    @Mock
    private AtualizarQuantidadeEstoqueUseCase atualizarEstoqueHandler;

    @Mock
    private VerificarDisponibilidadeEstoqueUseCase verificarDisponibilidadeHandler;

//    @Mock
//    private EstoqueMapper mapper;
//
//    @Mock
//    private EstoqueEntityMapper estoqueEntityMapper;

    @InjectMocks
    private EstoqueController estoqueController;

    @Test
    void cadastrar_deveRetornarEstoqueResponse_quandoEstoqueForCriado() {
        CriarEstoqueRequest request = new CriarEstoqueRequest("SKU123", 10);
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), "SKU123", 10);
        EstoqueResponse responseMock = new EstoqueResponse(UUID.randomUUID(), "SKU123", 10);

        when(cadastrarEstoqueHandler.cadastrar(request)).thenReturn(estoqueMock);
        //when(estoqueEntityMapper.toEntity(any())).thenReturn(new EstoqueEntity(UUID.randomUUID(), "SKU123", 10));
        //when(mapper.toResponse(estoqueMock)).thenReturn(responseMock);

        ResponseEntity<EstoqueResponse> response = estoqueController.cadastrar(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void consultarPorSku_deveRetornarEstoqueResponse_quandoEstoqueExistir() {
        String sku = "SKU123";
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 10);
        EstoqueResponse responseMock = new EstoqueResponse(UUID.randomUUID(), sku, 10);

        when(consultarEstoqueHandler.consultarPorSku(sku)).thenReturn(estoqueMock);
        //when(mapper.toResponse(estoqueMock)).thenReturn(responseMock);

        ResponseEntity<EstoqueResponse> response = estoqueController.consultarPorSku(sku);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void creditar_deveRetornarEstoqueResponse_quandoQuantidadeForCreditada() {
        String sku = "SKU123";
        AtualizarEstoqueRequest request = new AtualizarEstoqueRequest("SKU123", 5);
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 15);
        EstoqueResponse responseMock = new EstoqueResponse(UUID.randomUUID(), sku, 15);

        when(creditarEstoqueHandler.executar(sku, request.novaQuantidade())).thenReturn(estoqueMock);
        //when(mapper.toResponse(estoqueMock)).thenReturn(responseMock);

        ResponseEntity<EstoqueResponse> response = estoqueController.creditar(sku, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void debitar_deveRetornarEstoqueResponse_quandoQuantidadeForDebitada() {
        String sku = "SKU123";
        AtualizarEstoqueRequest request = new AtualizarEstoqueRequest("SKU123", 5);
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 5);
        EstoqueResponse responseMock = new EstoqueResponse(UUID.randomUUID(), sku, 5);

        when(debitarEstoqueHandler.debitarEstoque(sku, request.novaQuantidade())).thenReturn(estoqueMock);
        //when(mapper.toResponse(estoqueMock)).thenReturn(responseMock);

        ResponseEntity<EstoqueResponse> response = estoqueController.debitar(sku, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void atualizar_deveRetornarEstoqueResponse_quandoQuantidadeForAtualizada() {
        String sku = "SKU123";
        AtualizarEstoqueRequest request = new AtualizarEstoqueRequest("SKU123", 20);
        Estoque estoqueMock = new Estoque(UUID.randomUUID(), sku, 20);
        EstoqueResponse responseMock = new EstoqueResponse(UUID.randomUUID(), sku, 20);

        when(atualizarEstoqueHandler.atualizarQuantidadeEstoque(sku, request.novaQuantidade())).thenReturn(estoqueMock);
        //when(mapper.toResponse(estoqueMock)).thenReturn(responseMock);

        ResponseEntity<EstoqueResponse> response = estoqueController.atualizar(sku, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull( response.getBody());
    }

    @Test
    void verificarDisponibilidade_deveRetornarTrue_quandoQuantidadeEstiverDisponivel() {
        String sku = "SKU123";
        int quantidade = 5;

        when(verificarDisponibilidadeHandler.verificarDisponibilidade(sku, quantidade)).thenReturn(true);

        ResponseEntity<Boolean> response = estoqueController.verificarDisponibilidade(sku, quantidade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }
}