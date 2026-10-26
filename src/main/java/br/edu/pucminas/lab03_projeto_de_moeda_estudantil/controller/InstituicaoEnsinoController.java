package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.controller;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.InstituicaoEnsinoRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.InstituicaoEnsinoResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service.InstituicaoEnsinoService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoEnsinoController {

    private final InstituicaoEnsinoService service;

    public InstituicaoEnsinoController(InstituicaoEnsinoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InstituicaoEnsinoResponse> criar(@RequestBody InstituicaoEnsinoRequest request) {
        InstituicaoEnsinoResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/instituicoes/" + response.id())).body(response);
    }

    @GetMapping
    public List<InstituicaoEnsinoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public InstituicaoEnsinoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public InstituicaoEnsinoResponse atualizar(@PathVariable Long id, @RequestBody InstituicaoEnsinoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
