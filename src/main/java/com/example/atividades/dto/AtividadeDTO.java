package com.example.atividades.dto;

import com.example.atividades.model.SituacaoAtividade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeDTO {
    private Long id;

    @NotBlank(message = "Descrição da atividade é obrigatória")
    private String descricao;

    @NotNull(message = "Data de expiração é obrigatória")
    private LocalDateTime dataExpiracao;

    private SituacaoAtividade situacao;

    @NotNull(message = "ID da pessoa é obrigatório")
    private Long pessoaId;
}