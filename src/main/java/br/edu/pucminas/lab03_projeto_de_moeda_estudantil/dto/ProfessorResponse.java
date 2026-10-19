package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record ProfessorResponse(
        Long id,
        String nome,
        String email,
        String login,
        String cpf,
        String departamento,
        int saldo,
        InstituicaoEnsinoResponse instituicao) {
}