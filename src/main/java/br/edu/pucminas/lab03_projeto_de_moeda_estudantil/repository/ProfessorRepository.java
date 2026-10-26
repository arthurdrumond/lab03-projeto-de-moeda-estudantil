package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    Optional<Professor> findByCpf(String cpf);
}