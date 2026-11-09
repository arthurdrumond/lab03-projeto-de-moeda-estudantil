package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.empresaparceira;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.shared.EnderecoDTO;

public record EmpresaParceiraRequest(
        String nome,
        String email,
        String login,
        String senha,
        String cnpj,
        EnderecoDTO endereco) {
}
