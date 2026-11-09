package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.vantagem;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VantagemRepository extends JpaRepository<Vantagem, Long> {

    List<Vantagem> findByAtivaTrue();

    Optional<Vantagem> findByNomeAndEmpresaParceiraId(String nome, Long empresaParceiraId);
}
