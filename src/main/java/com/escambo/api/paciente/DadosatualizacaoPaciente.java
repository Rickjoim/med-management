package com.escambo.api.paciente;

import com.escambo.api.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosatualizacaoPaciente(@NotNull Long id, String nome, String telefone, DadosEndereco endereco) {
}
