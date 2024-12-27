package com.escambo.api.marcacao;

import com.escambo.api.endereco.DadosEndereco;
import com.escambo.api.endereco.Endereco;
import com.escambo.api.medico.DadosListagemMedicos;
import com.escambo.api.paciente.DadosListagemPacientes;

import java.util.Date;
import java.util.Optional;

public record DadosListagemMarcacao(Long id, Date data, String observacao, DadosListagemMedicos medico, DadosListagemPacientes paciente, DadosEndereco endereco) {
    public DadosListagemMarcacao (Marcacao marcacao){
        this(marcacao.getId(), marcacao.getData(), marcacao.getObservacao(), new DadosListagemMedicos(marcacao.getMedico()), new DadosListagemPacientes(marcacao.getPacientes()), new DadosEndereco(marcacao.getEndereco()));
    }

}
