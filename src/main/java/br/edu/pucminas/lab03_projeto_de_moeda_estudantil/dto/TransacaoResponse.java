package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto;

import java.time.LocalDateTime;

public record TransacaoResponse(
        Long id,
        String tipo,
        LocalDateTime dataHora,
        int valor,
        String descricao,
        String codigoCupom
) {}