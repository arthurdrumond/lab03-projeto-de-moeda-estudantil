package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model;

import jakarta.persistence.*;

@Entity
@Table(name = "professor")
public class Professor extends Usuario {

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private int saldo = 1000;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instituicao_id", nullable = false)
    private InstituicaoEnsino instituicao;

    public Professor() {
    }

    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public InstituicaoEnsino getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoEnsino instituicao) {
        this.instituicao = instituicao;
    }
}