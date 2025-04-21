package com.fiap.rm358568.edusocrates.estoque_service.API.controllers;

import com.fiap.rm358568.edusocrates.estoque_service.API.mappers.EstoqueMapper;
import com.fiap.rm358568.edusocrates.estoque_service.API.requests.AtualizarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.API.requests.CriarEstoqueRequest;
import com.fiap.rm358568.edusocrates.estoque_service.API.responses.EstoqueResponse;
import com.fiap.rm358568.edusocrates.estoque_service.aplicacao.usecases.*;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
@Tag(name = "Configurações Estoque!", description = "Operações relacionadas ao estoque!")
public class EstoqueController {

    private final CadastrarEstoqueUseCase cadastrarEstoqueHandler;
    private final ConsultarEstoquePorSkuUseCase consultarEstoqueHandler;
    private final CreditarEstoqueUseCase creditarEstoqueHandler;
    private final DebitarEstoqueUseCase debitarEstoqueHandler;
    private final AtualizarQuantidadeEstoqueUseCase atualizarEstoqueHandler;
    private final VerificarDisponibilidadeEstoqueUseCase verificarDisponibilidadeHandler;
    private final EstoqueMapper mapper;

    @PostMapping
    @Tag(name = "Cadastrar Estoque", description = "Cadastrar um novo estoque")
    @Operation(summary = "Cadastrar Estoque", description = "Cadastrar um novo estoque")
    public ResponseEntity<EstoqueResponse> cadastrar(@RequestBody @Valid CriarEstoqueRequest request) {
        Estoque salvo = cadastrarEstoqueHandler.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(salvo));
    }

    @GetMapping("/{sku}")
    @Tag(name = "Consultar Estoque", description = "Consultar estoque por SKU")
    @Operation(summary = "Consultar Estoque", description = "Consultar estoque por SKU")
    public ResponseEntity<EstoqueResponse> consultarPorSku(@PathVariable String sku) {
        Estoque estoque = consultarEstoqueHandler.consultarPorSku(sku);
        return ResponseEntity.ok(mapper.toResponse(estoque));
    }

    @PatchMapping("/{sku}/creditar")
    @Tag(name = "Creditar Estoque", description = "Creditar quantidade no estoque")
    @Operation(summary = "Creditar Estoque", description = "Creditar quantidade no estoque")
    public ResponseEntity<EstoqueResponse> creditar(@PathVariable String sku, @RequestBody @Valid AtualizarEstoqueRequest request) {
        Estoque atualizado = creditarEstoqueHandler.executar(sku, request.novaQuantidade());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @PatchMapping("/{sku}/debitar")
    @Tag(name = "Debitar Estoque", description = "Debitar quantidade no estoque")
    @Operation(summary = "Debitar Estoque", description = "Debitar quantidade no estoque")
    public ResponseEntity<EstoqueResponse> debitar(@PathVariable String sku, @RequestBody @Valid AtualizarEstoqueRequest request) {
        Estoque atualizado = debitarEstoqueHandler.debitarEstoque(sku, request.novaQuantidade());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @PatchMapping("/{sku}/atualizar")
    @Tag(name = "Atualizar Estoque", description = "Atualizar quantidade no estoque")
    @Operation(summary = "Atualizar Estoque", description = "Atualizar quantidade no estoque")
    public ResponseEntity<EstoqueResponse> atualizar(@PathVariable String sku, @RequestBody @Valid AtualizarEstoqueRequest request) {
        Estoque atualizado = atualizarEstoqueHandler.atualizarQuantidadeEstoque(sku, request.novaQuantidade());
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @GetMapping("/{sku}/disponivel")
    @Tag(name = "Verificar Disponibilidade", description = "Verificar disponibilidade do estoque")
    @Operation(summary = "Verificar Disponibilidade", description = "Verificar disponibilidade do estoque")
    public ResponseEntity<Boolean> verificarDisponibilidade(@PathVariable String sku, @RequestParam int quantidade) {
        boolean disponivel = verificarDisponibilidadeHandler.verificarDisponibilidade(sku, quantidade);
        return ResponseEntity.ok(disponivel);
    }
}
