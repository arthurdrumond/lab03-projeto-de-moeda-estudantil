package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

public record EnderecoDTO(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep) {
}
