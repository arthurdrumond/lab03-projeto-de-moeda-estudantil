package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.controller;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.ProfessorRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.ProfessorResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProfessorResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ProfessorResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProfessorResponse atualizar(@PathVariable Long id, @RequestBody ProfessorRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}