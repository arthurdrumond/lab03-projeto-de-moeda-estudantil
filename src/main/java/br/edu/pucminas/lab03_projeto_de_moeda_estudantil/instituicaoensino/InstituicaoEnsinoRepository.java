package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.instituicaoensino;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InstituicaoEnsinoRepository extends JpaRepository<InstituicaoEnsino, Long> {

    boolean existsByCnpj(String cnpj);

    Optional<InstituicaoEnsino> findByCnpj(String cnpj);
}
