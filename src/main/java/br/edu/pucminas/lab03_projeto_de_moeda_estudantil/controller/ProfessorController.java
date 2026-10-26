package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.controller;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.ProfessorRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.ProfessorResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfessorResponse> criar(@RequestBody ProfessorRequest request) {
        ProfessorResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/professores/" + response.id())).body(response);
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