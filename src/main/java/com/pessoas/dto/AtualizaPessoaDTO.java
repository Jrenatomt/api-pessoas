package com.pessoas.dto;

import com.pessoas.entidades.Pessoa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

public class AtualizaPessoaDTO {

    @NotBlank(message = "Preenchimento Obrigatório")
    private String nome;
    @NotNull(message = "Preenchimento Obrigatório")
    @PastOrPresent(message = "A data não pode ser do futuro")
    private LocalDate dataNascimento;

    public AtualizaPessoaDTO() {
    }

    public AtualizaPessoaDTO(String nome, LocalDate dataNascimento, List<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public AtualizaPessoaDTO(Pessoa pessoa) {
        this.nome = pessoa.getNome();
        this.dataNascimento = pessoa.getDataNascimento();
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

}
