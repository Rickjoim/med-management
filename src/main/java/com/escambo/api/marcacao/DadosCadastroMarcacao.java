package com.escambo.api.marcacao;

import com.escambo.api.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record DadosCadastroMarcacao(
        @NotNull
        Date data,

        @NotBlank
        String observacao,

        @NotNull @Valid
        DadosEndereco endereco,

        @NotNull
        Long medicoId,

        @NotNull
        Long pacienteId
) {}
