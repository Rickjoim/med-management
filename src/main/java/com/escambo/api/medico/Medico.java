package com.escambo.api.medico;

import com.escambo.api.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    private String telefone;
    private String email;

    private String crm;

    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico() {
    }

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.crm = dados.crm();
        this.ativo = true;
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCrm() {
        return this.crm;
    }

    public Especialidade getEspecialidade() {
        return this.especialidade;
    }

    public Long getID() {return this.id;}

    public void atualizarInformacoes(DadosatualizacaoMedico dados) {
        if(dados.nome()!= null){
            this.nome = dados.nome();
        }
        if(dados.telefone()!= null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco()!= null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo=false;
    }
}
