package com.fiap.rm358568.edusocrates.estoque_service.API.requests;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AtualizarEstoqueRequest(
        @NotBlank(message = "O SKU é obrigatório")
        String sku,

        @Min(value = 0, message = "A nova quantidade deve ser maior ou igual a 0")
        int novaQuantidade
) {}
