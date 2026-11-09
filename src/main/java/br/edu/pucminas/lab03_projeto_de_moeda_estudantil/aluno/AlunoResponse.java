package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.aluno;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.empresaparceira.EmpresaParceiraResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.instituicaoensino.InstituicaoEnsinoResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.shared.EnderecoDTO;

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
        EmpresaParceiraResponse empresaParceira,
        EnderecoDTO endereco) {
}
