package com.pessoas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pessoas.entidades.Pessoa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaDTO {

    private Long id;
    @NotBlank(message = "Preenchimento Obrigatório")
    private String nome;
    @NotNull
    @PastOrPresent(message = "A data não pode ser do futuro")
    private LocalDate dataNascimento;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @NotNull(message = "Preenchimento Obrigatório")
    private List<EnderecoDTO> enderecos;

    public PessoaDTO() {
    }

    public PessoaDTO(Long id, String nome, LocalDate dataNascimento, List<EnderecoDTO> enderecos) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
    }

    public PessoaDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.dataNascimento = pessoa.getDataNascimento();
        this.enderecos = pessoa.getEnderecos().stream().map(EnderecoDTO::new).collect(Collectors.toList());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
