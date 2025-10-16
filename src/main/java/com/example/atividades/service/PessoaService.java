package com.example.atividades.service;

import com.example.atividades.dto.PessoaDTO;
import com.example.atividades.model.Pessoa;
import com.example.atividades.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public PessoaDTO criar(PessoaDTO dto) {
        if (pessoaRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setEmail(dto.getEmail());

        pessoa = pessoaRepository.save(pessoa);
        return toDTO(pessoa);
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> listarTodas() {
        return pessoaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaDTO buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        return toDTO(pessoa);
    }

    @Transactional
    public PessoaDTO atualizar(Long id, PessoaDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        if (!pessoa.getEmail().equals(dto.getEmail()) &&
                pessoaRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        pessoa.setNome(dto.getNome());
        pessoa.setEmail(dto.getEmail());

        pessoa = pessoaRepository.save(pessoa);
        return toDTO(pessoa);
    }

    @Transactional
    public void deletar(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    private PessoaDTO toDTO(Pessoa pessoa) {
        return new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail());
    }
}