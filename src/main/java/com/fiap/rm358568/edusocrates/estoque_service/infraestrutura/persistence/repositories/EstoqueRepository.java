package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.repositories;

import com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.entities.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueEntity, UUID> {

    Optional<EstoqueEntity> findBySku(String sku);

    void deleteBySku(String sku);
}
