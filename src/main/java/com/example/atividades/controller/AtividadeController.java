package com.example.atividades.controller;

import com.example.atividades.dto.AtividadeDTO;
import com.example.atividades.service.AtividadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<AtividadeDTO> criar(@Valid @RequestBody AtividadeDTO dto) {
        AtividadeDTO criada = atividadeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<AtividadeDTO>> listarTodas() {
        List<AtividadeDTO> atividades = atividadeService.listarTodas();
        return ResponseEntity.ok(atividades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeDTO> buscarPorId(@PathVariable Long id) {
        AtividadeDTO atividade = atividadeService.buscarPorId(id);
        return ResponseEntity.ok(atividade);
    }

    @GetMapping("/ordenadas-por-expiracao")
    public ResponseEntity<List<AtividadeDTO>> listarPorOrdemExpiracao() {
        List<AtividadeDTO> atividades = atividadeService.listarPorOrdemExpiracao();
        return ResponseEntity.ok(atividades);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtividadeDTO dto) {
        AtividadeDTO atualizada = atividadeService.atualizar(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<AtividadeDTO> concluir(@PathVariable Long id) {
        AtividadeDTO concluida = atividadeService.concluir(id);
        return ResponseEntity.ok(concluida);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atividadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}