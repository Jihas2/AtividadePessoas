package com.example.atividades.service;

import com.example.atividades.dto.EnderecoDTO;
import com.example.atividades.model.Endereco;
import com.example.atividades.model.Pessoa;
import com.example.atividades.repository.EnderecoRepository;
import com.example.atividades.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public EnderecoDTO criar(EnderecoDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setPessoa(pessoa);

        endereco = enderecoRepository.save(endereco);
        return toDTO(endereco);
    }

    @Transactional(readOnly = true)
    public List<EnderecoDTO> listarTodos() {
        return enderecoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EnderecoDTO buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        return toDTO(endereco);
    }

    @Transactional(readOnly = true)
    public List<EnderecoDTO> listarPorPessoa(Long pessoaId) {
        return enderecoRepository.findByPessoaId(pessoaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnderecoDTO atualizar(Long id, EnderecoDTO dto) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        endereco.setLogradouro(dto.getLogradouro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());

        endereco = enderecoRepository.save(endereco);
        return toDTO(endereco);
    }

    @Transactional
    public void deletar(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }

    private EnderecoDTO toDTO(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPessoa().getId()
        );
    }
}