package com.example.atividades.controller;

import com.example.atividades.dto.PessoaDTO;
import com.example.atividades.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@Valid @RequestBody PessoaDTO dto) {
        PessoaDTO criada = pessoaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodas() {
        List<PessoaDTO> pessoas = pessoaService.listarTodas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) {
        PessoaDTO pessoa = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PessoaDTO dto) {
        PessoaDTO atualizada = pessoaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}