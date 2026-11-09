package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.moeda;

public record ResgateRequest(
        Long alunoId,
        Long vantagemId
) {}