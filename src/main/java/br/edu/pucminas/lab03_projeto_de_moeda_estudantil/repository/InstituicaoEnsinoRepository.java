package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.InstituicaoEnsino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoEnsinoRepository extends JpaRepository<InstituicaoEnsino, Long> {

    boolean existsByCnpj(String cnpj);
}
