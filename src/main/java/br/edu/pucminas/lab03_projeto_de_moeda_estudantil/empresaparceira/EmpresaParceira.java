package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.empresaparceira;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.shared.Endereco;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.shared.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa_parceira")
public class EmpresaParceira extends Usuario {

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Embedded
    private Endereco endereco;

    public EmpresaParceira() {
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
