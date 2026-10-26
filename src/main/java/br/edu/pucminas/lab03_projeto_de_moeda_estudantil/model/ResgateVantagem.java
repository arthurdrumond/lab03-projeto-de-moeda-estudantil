package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "resgate_vantagem")
public class ResgateVantagem extends Transacao {

    @Column(name = "codigo_cupom", nullable = false, unique = true)
    private String codigoCupom;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno resgatadoPor;

    @ManyToOne
    @JoinColumn(name = "vantagem_id", nullable = false)
    private Vantagem referente;

    public ResgateVantagem() {
    }

    public String getCodigoCupom() {
        return codigoCupom;
    }

    public void setCodigoCupom(String codigoCupom) {
        this.codigoCupom = codigoCupom;
    }

    public Aluno getResgatadoPor() {
        return resgatadoPor;
    }

    public void setResgatadoPor(Aluno resgatadoPor) {
        this.resgatadoPor = resgatadoPor;
    }

    public Vantagem getReferente() {
        return referente;
    }

    public void setReferente(Vantagem referente) {
        this.referente = referente;
    }

    public String gerarCodigo() {
        codigoCupom = UUID.randomUUID().toString();
        return codigoCupom;
    }
}
