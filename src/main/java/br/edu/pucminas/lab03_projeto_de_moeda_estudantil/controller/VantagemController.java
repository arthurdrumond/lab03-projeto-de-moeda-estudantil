package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.controller;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.VantagemRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.VantagemResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service.VantagemService;
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
@RequestMapping("/vantagens")
public class VantagemController {

    private final VantagemService service;

    public VantagemController(VantagemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VantagemResponse> criar(@RequestBody VantagemRequest request) {
        VantagemResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/vantagens/" + response.id())).body(response);
    }

    @GetMapping
    public List<VantagemResponse> listar() {
        return service.listar();
    }

    @GetMapping("/ativas")
    public List<VantagemResponse> listarAtivas() {
        return service.listarAtivas();
    }

    @GetMapping("/{id}")
    public VantagemResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public VantagemResponse atualizar(@PathVariable Long id, @RequestBody VantagemRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
