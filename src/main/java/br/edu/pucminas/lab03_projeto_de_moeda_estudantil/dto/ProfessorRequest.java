package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record ProfessorRequest(
        String nome,
        String email,
        String login,
        String senha,
        String cpf,
        String departamento,
        Long instituicaoId) {
}