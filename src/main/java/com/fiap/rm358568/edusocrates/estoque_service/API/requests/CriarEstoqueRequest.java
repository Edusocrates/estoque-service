package com.fiap.rm358568.edusocrates.estoque_service.API.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CriarEstoqueRequest(
        @NotBlank(message = "O SKU é obrigatório")
        String sku,

        @Min(value = 0, message = "A quantidade disponível deve ser maior ou igual a 0")
        int quantidadeDisponivel
) {}
