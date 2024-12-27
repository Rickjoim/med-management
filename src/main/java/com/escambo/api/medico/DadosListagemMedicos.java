package com.escambo.api.medico;

public record DadosListagemMedicos(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public DadosListagemMedicos (Medico medico){
        this(medico.getID(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
