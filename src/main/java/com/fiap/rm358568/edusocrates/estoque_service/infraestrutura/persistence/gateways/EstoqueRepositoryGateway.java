package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.gateways;

import com.fiap.rm358568.edusocrates.estoque_service.dominio.entities.Estoque;
import com.fiap.rm358568.edusocrates.estoque_service.dominio.gateways.EstoqueGateway;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.mappers.EstoqueEntityMapper;
import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.repositories.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EstoqueRepositoryGateway implements EstoqueGateway {

    private final EstoqueRepository repository;

    @Override
    public Estoque salvar(Estoque estoque) {
        log.info("Salvando ou atualizando estoque na base de dados!");

        var entity = EstoqueEntityMapper.toEntity(estoque);

        repository.findBySku(entity.getSku()).ifPresent(existingEntity -> {
            entity.setId(existingEntity.getId());
        });

        var salvo = repository.save(entity);
        log.info("Estoque salvo ou atualizado com sucesso!");
        return EstoqueEntityMapper.toDomain(salvo);
    }

    @Override
    public Optional<Estoque> buscarPorSku(String sku) {
        log.info("Buscando estoque para o SKU: {} na base de dados!", sku);
        return repository.findBySku(sku)
                .map(EstoqueEntityMapper::toDomain);
    }

    @Override
    public void deletarPorSku(String sku) {
        log.info("Deletando estoque para o SKU: {} na base de dados!", sku);
        repository.deleteBySku(sku);
    }
}
