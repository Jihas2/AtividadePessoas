package com.example.atividades.service;

import com.example.atividades.dto.AtividadeDTO;
import com.example.atividades.model.Atividade;
import com.example.atividades.model.Pessoa;
import com.example.atividades.model.SituacaoAtividade;
import com.example.atividades.repository.AtividadeRepository;
import com.example.atividades.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public AtividadeDTO criar(AtividadeDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Atividade atividade = new Atividade();
        atividade.setDescricao(dto.getDescricao());
        atividade.setDataExpiracao(dto.getDataExpiracao());
        atividade.setSituacao(SituacaoAtividade.PENDENTE);
        atividade.setPessoa(pessoa);

        atividade = atividadeRepository.save(atividade);
        return toDTO(atividade);
    }

    @Transactional(readOnly = true)
    public List<AtividadeDTO> listarTodas() {
        return atividadeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AtividadeDTO buscarPorId(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
        return toDTO(atividade);
    }

    @Transactional(readOnly = true)
    public List<AtividadeDTO> listarPorOrdemExpiracao() {
        return atividadeRepository.findAllByOrderByDataExpiracaoAsc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AtividadeDTO atualizar(Long id, AtividadeDTO dto) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));

        atividade.setDescricao(dto.getDescricao());
        atividade.setDataExpiracao(dto.getDataExpiracao());
        if (dto.getSituacao() != null) {
            atividade.setSituacao(dto.getSituacao());
        }

        atividade = atividadeRepository.save(atividade);
        return toDTO(atividade);
    }

    @Transactional
    public AtividadeDTO concluir(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));

        atividade.setSituacao(SituacaoAtividade.CONCLUIDA);
        atividade = atividadeRepository.save(atividade);
        return toDTO(atividade);
    }

    @Transactional
    public void deletar(Long id) {
        if (!atividadeRepository.existsById(id)) {
            throw new RuntimeException("Atividade não encontrada");
        }
        atividadeRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 * * * *") // Executa a cada hora
    @Transactional
    public void atualizarAtividadesExpiradas() {
        List<Atividade> atividadesExpiradas = atividadeRepository
                .findBySituacaoAndDataExpiracaoBefore(
                        SituacaoAtividade.PENDENTE,
                        LocalDateTime.now()
                );

        atividadesExpiradas.forEach(atividade ->
                atividade.setSituacao(SituacaoAtividade.EXPIRADA)
        );

        atividadeRepository.saveAll(atividadesExpiradas);
    }

    private AtividadeDTO toDTO(Atividade atividade) {
        return new AtividadeDTO(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getDataExpiracao(),
                atividade.getSituacao(),
                atividade.getPessoa().getId()
        );
    }
}