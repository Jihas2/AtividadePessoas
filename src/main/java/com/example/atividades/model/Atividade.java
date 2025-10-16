package com.example.atividades.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "atividades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição da atividade é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Data de expiração é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoAtividade situacao = SituacaoAtividade.PENDENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    @JsonBackReference("pessoa-atividade")
    private Pessoa pessoa;
}