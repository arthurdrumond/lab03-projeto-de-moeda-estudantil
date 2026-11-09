package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.moeda;

import java.util.List;

public record ExtratoResponse(
        int saldoAtual,
        List<TransacaoResponse> transacoes
) {}