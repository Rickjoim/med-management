package com.escambo.api.endereco;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor // Construtor sem argumentos obrigatório para JPA
public class Endereco {

    @NotBlank(message = "Logradouro é obrigatório.")
    @Size(max = 100, message = "Logradouro deve ter no máximo 100 caracteres.")
    private String logradouro;

    @NotBlank(message = "Bairro é obrigatório.")
    @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres.")
    private String bairro;

    @NotBlank(message = "CEP é obrigatório.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve seguir o formato 99999-999.")
    private String cep;

    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres.")
    private String numero;

    @Size(max = 50, message = "Complemento deve ter no máximo 50 caracteres.")
    private String complemento;

    @NotBlank(message = "Cidade é obrigatória.")
    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres.")
    private String cidade;

    @NotBlank(message = "UF é obrigatório.")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter exatamente 2 letras maiúsculas.")
    private String uf;

    public Endereco() {

    }

    // Método para definir dados após criação da entidade
    public void setDados(String logradouro, String bairro, String cep, String numero, String complemento, String cidade, String uf) {
        this.logradouro = sanitize(logradouro);
        this.bairro = sanitize(bairro);
        this.cep = sanitize(cep);
        this.numero = sanitize(numero);
        this.complemento = sanitize(complemento);
        this.cidade = sanitize(cidade);
        this.uf = sanitize(uf);
    }

    // Método utilitário para evitar valores nulos e limpar espaços
    private static String sanitize(String value) {
        return value == null ? null : value.trim();
    }

    // Construtor utilizando um DTO (DadosEndereco)
    public Endereco(DadosEndereco dados) {
        this();  // Chama o construtor sem argumentos
        setDados(
                dados.logradouro(),
                dados.bairro(),
                dados.cep(),
                dados.numero(),
                dados.complemento(),
                dados.cidade(),
                dados.uf()
        );
    }

    public void atualizarInformacoes(DadosEndereco endereco) {
        if(endereco.logradouro()!= null){
            this.logradouro = endereco.logradouro();
        }
        if(endereco.bairro()!= null){
            this.bairro = endereco.bairro();
        }
        if(endereco.cep()!= null){
            this.cep = endereco.cep();
        }
        if(endereco.numero()!= null){
            this.numero = endereco.numero();
        }
        if(endereco.complemento()!= null){
            this.complemento = endereco.complemento();
        }
        if(endereco.cidade()!= null){
            this.cidade = endereco.cidade();
        }
        if(endereco.uf()!= null){
            this.uf = endereco.uf();
        }
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public String getBairro() {
        return this.bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public String getCidade() {
        return this.cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public String getNumero() {
        return this.numero;
    }
}
