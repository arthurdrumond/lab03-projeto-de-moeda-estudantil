package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record TransferenciaRequest(
        Long professorId,
        Long empresaParceiraId,
        Long alunoId,
        int valor,
        String motivo
) {}