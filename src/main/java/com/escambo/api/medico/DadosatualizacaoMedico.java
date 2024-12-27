package com.escambo.api.medico;

import com.escambo.api.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosatualizacaoMedico(@NotNull Long id, String nome, String telefone, DadosEndereco endereco) {
}
