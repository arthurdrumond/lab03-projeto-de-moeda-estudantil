package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.moeda;

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
    public ResponseEntity<TransferenciaMoedas> transferir(@RequestBody TransferenciaRequest request) {
        TransferenciaMoedas transferencia = moedaService.transferir(request);
        return ResponseEntity.ok(transferencia);
    }

    @PostMapping("/moedas/resgatar")
    public ResponseEntity<ResgateVantagem> resgatar(@RequestBody ResgateRequest request) {
        ResgateVantagem resgate = moedaService.resgatar(request);
        return ResponseEntity.ok(resgate);
    }

    @GetMapping("/alunos/{id}/extrato")
    public ResponseEntity<ExtratoResponse> obterExtratoAluno(@PathVariable Long id) {
        return ResponseEntity.ok(moedaService.obterExtratoAluno(id));
    }

    @GetMapping("/professores/{id}/extrato")
    public ResponseEntity<ExtratoResponse> obterExtratoProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(moedaService.obterExtratoProfessor(id));
    }

    @GetMapping("/empresas-parceiras/{id}/extrato")
    public ResponseEntity<ExtratoResponse> obterExtratoEmpresaParceira(@PathVariable Long id) {
        return ResponseEntity.ok(moedaService.obterExtratoEmpresaParceira(id));
    }
}