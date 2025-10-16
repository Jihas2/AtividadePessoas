package com.example.atividades.repository;

import com.example.atividades.model.Atividade;
import com.example.atividades.model.SituacaoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByPessoaIdOrderByDataExpiracaoAsc(Long pessoaId);
    List<Atividade> findAllByOrderByDataExpiracaoAsc();
    List<Atividade> findBySituacaoAndDataExpiracaoBefore(SituacaoAtividade situacao, LocalDateTime data);
}