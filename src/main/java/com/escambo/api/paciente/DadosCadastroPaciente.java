package com.escambo.api.paciente;


import com.escambo.api.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,

        @NotBlank
        String telefone,
        @NotBlank
        @Email
        String email,

        @NotNull @Valid DadosEndereco endereco) {
}
