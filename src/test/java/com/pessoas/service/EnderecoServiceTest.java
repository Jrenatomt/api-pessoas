package com.pessoas.service;

import com.pessoas.dto.EnderecoDTO;
import com.pessoas.entidades.Endereco;
import com.pessoas.entidades.Pessoa;
import com.pessoas.repositories.EnderecoRepository;
import com.pessoas.repositories.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    void deveRetornarListaDeEnderecos() {
        Long pessoaId = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco());
        enderecos.add(new Endereco());
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.findByPessoa(pessoa)).thenReturn(enderecos);

        List<EnderecoDTO> enderecosRetornados = enderecoService.listaEnderecosPessoa(pessoaId);

        assertThat(enderecosRetornados).hasSize(2);
    }
}