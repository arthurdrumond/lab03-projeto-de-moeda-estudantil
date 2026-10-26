package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record VantagemResponse(
        Long id,
        String nome,
        String descricao,
        String foto,
        int custoMoedas,
        boolean ativa,
        EmpresaParceiraResponse empresaParceira) {
}
