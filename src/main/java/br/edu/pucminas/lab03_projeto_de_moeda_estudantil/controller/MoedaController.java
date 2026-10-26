package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.controller;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.*;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service.MoedaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MoedaController {

    private final MoedaService moedaService;

    public MoedaController(MoedaService moedaService) {
        this.moedaService = moedaService;
    }

    @PostMapping("/moedas/distribuir-semestral")
    public ResponseEntity<String> distribuirSemestral() {
        moedaService.distribuirMoedasSemestrais();
        return ResponseEntity.ok("Crédito de 1.000 moedas distribuído com sucesso para todos os professores!");
    }

    @PostMapping("/moedas/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransferenciaRequest request) {
        try {
            return ResponseEntity.ok(moedaService.transferir(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/moedas/resgatar")
    public ResponseEntity<?> resgatar(@RequestBody ResgateRequest request) {
        try {
            return ResponseEntity.ok(moedaService.resgatar(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/alunos/{id}/extrato")
    public ResponseEntity<ExtratoResponse> obterExtratoAluno(@PathVariable Long id) {
        return ResponseEntity.ok(moedaService.obterExtratoAluno(id));
    }

    @GetMapping("/professores/{id}/extrato")
    public ResponseEntity<ExtratoResponse> obterExtratoProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(moedaService.obterExtratoProfessor(id));
    }
}