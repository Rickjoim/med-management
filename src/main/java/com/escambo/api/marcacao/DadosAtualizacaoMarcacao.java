package com.escambo.api.marcacao;

import com.escambo.api.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record DadosAtualizacaoMarcacao(
        @NotNull Long id,
        Date data,
        String observacao,
        DadosEndereco endereco
) {
}
