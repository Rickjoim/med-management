package com.escambo.api.paciente;

import com.escambo.api.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    private String telefone;
    private String email;

    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente() {
    }


    public Paciente(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.ativo = true;
        this.endereco = new Endereco(dados.endereco());
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public Long getId() {
        return this.id;
    }

    public void atualizarInformacoes(DadosatualizacaoPaciente dados) {
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
        this.ativo = false;
    }
}
