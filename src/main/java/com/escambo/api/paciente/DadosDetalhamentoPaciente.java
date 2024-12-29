package com.escambo.api.paciente;

import com.escambo.api.endereco.Endereco;
import com.escambo.api.medico.Especialidade;
import com.escambo.api.medico.Medico;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String telefone, Endereco endereco) {
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());

    }
}
