package com.example.atividades.controller;

import com.example.atividades.dto.EnderecoDTO;
import com.example.atividades.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoDTO> criar(@Valid @RequestBody EnderecoDTO dto) {
        EnderecoDTO criado = enderecoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarTodos() {
        List<EnderecoDTO> enderecos = enderecoService.listarTodos();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Long id) {
        EnderecoDTO endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<EnderecoDTO>> listarPorPessoa(@PathVariable Long pessoaId) {
        List<EnderecoDTO> enderecos = enderecoService.listarPorPessoa(pessoaId);
        return ResponseEntity.ok(enderecos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoDTO dto) {
        EnderecoDTO atualizado = enderecoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}