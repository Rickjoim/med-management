package com.escambo.api.paciente;

public record DadosListagemPacientes(Long id, String nome, String email) {
    public DadosListagemPacientes(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail());
    }
}
