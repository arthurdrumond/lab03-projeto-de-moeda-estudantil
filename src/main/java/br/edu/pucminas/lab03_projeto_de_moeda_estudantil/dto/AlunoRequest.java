package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record AlunoRequest(
        String nome,
        String email,
        String login,
        String senha,
        String cpf,
        String rg,
        String curso,
        Long instituicaoId,
        EnderecoDTO endereco) {
}
