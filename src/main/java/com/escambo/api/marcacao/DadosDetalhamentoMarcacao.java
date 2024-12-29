package com.escambo.api.marcacao;

import com.escambo.api.endereco.Endereco;
import com.escambo.api.medico.Medico;
import com.escambo.api.paciente.Paciente;

import java.util.Date;

public record DadosDetalhamentoMarcacao(Long id, Date data, String observacao, Endereco endereco, Medico medico, Paciente paciente) {
    public DadosDetalhamentoMarcacao(Marcacao marcacao) {
        this(marcacao.getId(), marcacao.getData(), marcacao.getObservacao(), marcacao.getEndereco(), marcacao.getMedico(), marcacao.getPacientes());

    }
}
