package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.moeda;

public record TransferenciaRequest(
        Long professorId,
        Long empresaParceiraId,
        Long alunoId,
        int valor,
        String motivo
) {}