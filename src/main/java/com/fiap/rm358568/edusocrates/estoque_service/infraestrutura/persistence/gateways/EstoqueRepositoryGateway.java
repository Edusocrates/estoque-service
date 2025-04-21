package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.gateways;

import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.mappers.EstoqueEntityMapper;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.repositories.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EstoqueRepositoryGateway implements EstoqueGateway {

    private final EstoqueRepository repository;

    @Override
    public Estoque salvar(Estoque estoque) {
        var entity = EstoqueEntityMapper.toEntity(estoque);
        var salvo = repository.save(entity);
        return EstoqueEntityMapper.toDomain(salvo);
    }

    @Override
    public Optional<Estoque> buscarPorSku(String sku) {
        return repository.findBySku(sku)
                .map(EstoqueEntityMapper::toDomain);
    }

    @Override
    public void deletarPorSku(String sku) {
        repository.deleteBySku(sku);
    }
}
