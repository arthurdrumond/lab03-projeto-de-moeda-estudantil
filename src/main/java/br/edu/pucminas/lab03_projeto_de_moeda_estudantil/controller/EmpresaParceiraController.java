package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.controller;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.EmpresaParceiraRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.EmpresaParceiraResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service.EmpresaParceiraService;
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
@RequestMapping("/empresas-parceiras")
public class EmpresaParceiraController {

    private final EmpresaParceiraService service;

    public EmpresaParceiraController(EmpresaParceiraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmpresaParceiraResponse> criar(@RequestBody EmpresaParceiraRequest request) {
        EmpresaParceiraResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/empresas-parceiras/" + response.id())).body(response);
    }

    @GetMapping
    public List<EmpresaParceiraResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EmpresaParceiraResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EmpresaParceiraResponse atualizar(@PathVariable Long id, @RequestBody EmpresaParceiraRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
