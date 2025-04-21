package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PRODUTO_CRIADO_QUEUE = "produto.criado.estoque";
    public static final String ESTOQUE_ATUALIZADO_EXCHANGE = "estoque.atualizado.exchange";
    public static final String ESTOQUE_ATUALIZADO_ROUTING_KEY = "estoque.atualizado";

    @Bean
    public Queue produtoCriadoQueue() {
        return new Queue(PRODUTO_CRIADO_QUEUE, true);
    }

    @Bean
    public DirectExchange estoqueAtualizadoExchange() {
        return new DirectExchange(ESTOQUE_ATUALIZADO_EXCHANGE);
    }

    @Bean
    public Binding bindingEstoqueAtualizado() {
        return BindingBuilder
                .bind(produtoCriadoQueue())
                .to(estoqueAtualizadoExchange())
                .with(ESTOQUE_ATUALIZADO_ROUTING_KEY);
    }
}
