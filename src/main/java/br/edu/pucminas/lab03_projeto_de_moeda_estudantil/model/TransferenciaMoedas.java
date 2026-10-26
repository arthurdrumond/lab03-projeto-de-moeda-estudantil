package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transferencia_moedas")
public class TransferenciaMoedas extends Transacao {

    @Column(nullable = false)
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor remetente;

    @ManyToOne
    @JoinColumn(name = "empresa_parceira_id")
    private EmpresaParceira empresaParceira;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Aluno destinatario;

    public TransferenciaMoedas() {
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Professor getRemetente() {
        return remetente;
    }

    public void setRemetente(Professor remetente) {
        this.remetente = remetente;
    }

    public EmpresaParceira getEmpresaParceira() {
        return empresaParceira;
    }

    public void setEmpresaParceira(EmpresaParceira empresaParceira) {
        this.empresaParceira = empresaParceira;
    }

    public Aluno getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Aluno destinatario) {
        this.destinatario = destinatario;
    }
}
