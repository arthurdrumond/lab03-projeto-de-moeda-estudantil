package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.EmpresaParceira;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmpresaParceiraRepository extends JpaRepository<EmpresaParceira, Long> {

    boolean existsByCnpj(String cnpj);

    Optional<EmpresaParceira> findByCnpj(String cnpj);
}
