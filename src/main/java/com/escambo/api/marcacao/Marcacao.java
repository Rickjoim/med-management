package com.escambo.api.marcacao;

import com.escambo.api.endereco.DadosEndereco;
import com.escambo.api.endereco.Endereco;
import com.escambo.api.medico.Medico;
import com.escambo.api.paciente.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "marcacao")
@Entity(name = "Marcacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Marcacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Embedded
    private Endereco endereco;

    private String observacao;

    private Boolean ativo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    public Marcacao(){}
    public Marcacao(DadosCadastroMarcacao dados, Medico medico, Paciente paciente) {
        this.ativo = true;
        this.data = dados.data();
        this.observacao = dados.observacao();
        this.endereco = new Endereco(dados.endereco());
        this.medico = medico;
        this.paciente = paciente;
    }

    public Long getId() {
        return this.id;
    }

    public Date getData() {return this.data;}

    public String getObservacao() {
        return this.observacao;
    }

    public Medico getMedico() {
        return this.medico;
    }

    public Paciente getPacientes() {
        return this.paciente;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void atualizarInformacoes(DadosAtualizacaoMarcacao dados) {

        if (dados.data() != null) {
            this.data = dados.data();
        }
        if (dados.observacao() != null) {
            this.observacao = dados.observacao();
        }
        if(dados.endereco()!= null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }

    }
    public void excluir() {
        this.ativo=false;
    }

}
