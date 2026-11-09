package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.shared;

public record EnderecoDTO(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep) {
}
