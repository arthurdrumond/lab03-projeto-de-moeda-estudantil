package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.aluno;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.shared.EnderecoDTO;

public record AlunoRequest(
        String nome,
        String email,
        String login,
        String senha,
        String cpf,
        String rg,
        String curso,
        Long instituicaoId,
        Long empresaParceiraId,
        EnderecoDTO endereco) {
}
