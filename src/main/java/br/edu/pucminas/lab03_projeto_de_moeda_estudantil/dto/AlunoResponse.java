package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record AlunoResponse(
        Long id,
        String nome,
        String email,
        String login,
        String cpf,
        String rg,
        String curso,
        int saldo,
        InstituicaoEnsinoResponse instituicao,
        EnderecoDTO endereco) {
}
