package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t FROM Transacao t WHERE TYPE(t) = TransferenciaMoedas AND t.destinatario.id = :alunoId OR TYPE(t) = ResgateVantagem AND t.resgatadoPor.id = :alunoId ORDER BY t.dataHora DESC")
    List<Transacao> findByAlunoId(@Param("alunoId") Long alunoId);

    @Query("SELECT t FROM Transacao t WHERE TYPE(t) = TransferenciaMoedas AND t.remetente.id = :professorId OR TYPE(t) = CreditoSemestral AND t.professor.id = :professorId ORDER BY t.dataHora DESC")
    List<Transacao> findByProfessorId(@Param("professorId") Long professorId);
}