package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.vantagem;

public record VantagemRequest(
        String nome,
        String descricao,
        String foto,
        int custoMoedas,
        Long empresaParceiraId,
        Boolean ativa) {
}
