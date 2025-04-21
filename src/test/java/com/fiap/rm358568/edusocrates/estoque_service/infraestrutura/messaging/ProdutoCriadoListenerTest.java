package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging;


import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.CadastrarEstoqueUseCase;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.config.RabbitMQConfig;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.events.ProdutoCriadoEvent;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.messaging.listeners.ProdutoCriadoListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoCriadoListenerTest {

    @Mock
    private CadastrarEstoqueUseCase cadastrarEstoqueUseCase;

    @InjectMocks
    private ProdutoCriadoListener produtoCriadoListener;

    @Test
    void onProdutoCriado_deveCadastrarEstoque_quandoEventoForRecebido() {
        // Arrange
        ProdutoCriadoEvent event = new ProdutoCriadoEvent("SKU123");
        CriarEstoqueRequest expectedRequest = new CriarEstoqueRequest(event.getSku(), 0);

        // Act
        produtoCriadoListener.onProdutoCriado(event);

        // Assert
        verify(cadastrarEstoqueUseCase).cadastrar(expectedRequest);
    }

    @Test
    void onProdutoCriado_deveTerAnotacaoRabbitListener() throws NoSuchMethodException {
        // Assert
        RabbitListener annotation = ProdutoCriadoListener.class
                .getMethod("onProdutoCriado", ProdutoCriadoEvent.class)
                .getAnnotation(RabbitListener.class);

        assertNotNull(annotation);
        assertEquals(RabbitMQConfig.PRODUTO_CRIADO_QUEUE, annotation.queues()[0]);
    }
}
