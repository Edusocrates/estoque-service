package com.fiap.rm358568.edusocrates.estoque_service.infraestrutura.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "estoques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstoqueEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(name = "quantidade_disponivel", nullable = false)
    private int quantidadeDisponivel;
}
