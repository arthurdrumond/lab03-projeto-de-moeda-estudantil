package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.vantagem;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.empresaparceira.EmpresaParceiraResponse;

public record VantagemResponse(
        Long id,
        String nome,
        String descricao,
        String foto,
        int custoMoedas,
        boolean ativa,
        EmpresaParceiraResponse empresaParceira) {
}
