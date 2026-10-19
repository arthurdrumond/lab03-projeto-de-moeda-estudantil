package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record EmpresaParceiraResponse(
        Long id,
        String nome,
        String email,
        String login,
        String cnpj,
        EnderecoDTO endereco) {
}
