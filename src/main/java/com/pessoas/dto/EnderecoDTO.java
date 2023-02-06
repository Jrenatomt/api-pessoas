package com.pessoas.dto;

import com.pessoas.entidades.Endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EnderecoDTO {

    @NotBlank(message = "Preenchimento Obrigatório")
    private String rua;
    @NotNull(message = "Preenchimento Obrigatório")
    private int numero;
    @NotBlank(message = "Preenchimento Obrigatório")
    private String bairro;
    @NotBlank(message = "Preenchimento Obrigatório")
    private String cep;
    @NotBlank(message = "Preenchimento Obrigatório")
    private String cidade;
    @NotBlank(message = "Preenchimento Obrigatório")
    private String estado;
    @NotNull(message = "Preenchimento Obrigatório")
    private boolean principal;

    public EnderecoDTO() {
    }


    public EnderecoDTO(Endereco endereco) {
        this.rua = endereco.getRua();
        this.bairro = endereco.getBairro();
        this.numero = endereco.getNumero();
        this.cep = endereco.getCep();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.principal = endereco.isPrincipal();
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isPrincipal() {
        return principal;
    }

}
