package com.pessoas.service;

import com.pessoas.dto.AtualizaPessoaDTO;
import com.pessoas.dto.EnderecoDTO;
import com.pessoas.dto.PessoaDTO;
import com.pessoas.entidades.Endereco;
import com.pessoas.entidades.Pessoa;
import com.pessoas.repositories.EnderecoRepository;
import com.pessoas.repositories.PessoaRepository;
import com.pessoas.service.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    //faz a busca pelo id da pessoa
    @Transactional(readOnly = true)
    public PessoaDTO buscarPeloId(Long pessoaId){
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        return new PessoaDTO(pessoa);
    }

    //Atualiza dados da pessoa(nome data e data nascimento)
    @Transactional
    public PessoaDTO atualizaPessoa(Long pessoaId, AtualizaPessoaDTO atualizaPessoaDTO){
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        pessoa.setNome(atualizaPessoaDTO.getNome());
        pessoa.setDataNascimento(atualizaPessoaDTO.getDataNascimento());
        pessoa = pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }

    //busca todas as pessoas (adicionar paginação)
    @Transactional(readOnly = true)
    public List<PessoaDTO> BuscarTodasPessoas(){
        List<Pessoa> list = pessoaRepository.findAll();
        return list.stream().map(PessoaDTO::new).collect(Collectors.toList());
    }

    // salva uma pessoa
    @Transactional
    public PessoaDTO salvaPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoa = pessoaRepository.save(pessoa);

        List<EnderecoDTO> enderecosDTO = pessoaDTO.getEnderecos();
        if (enderecosDTO != null) {
            Pessoa finalPessoa = pessoa;
            List<Endereco> enderecos = enderecosDTO.stream()
                    .map(enderecoDTO -> {
                        Endereco endereco = new Endereco();
                        endereco.setRua(enderecoDTO.getRua());
                        endereco.setBairro(enderecoDTO.getBairro());
                        endereco.setCep(enderecoDTO.getCep());
                        endereco.setNumero(enderecoDTO.getNumero());
                        endereco.setCidade(enderecoDTO.getCidade());
                        endereco.setEstado(enderecoDTO.getEstado());
                        endereco.setPrincipal(enderecoDTO.isPrincipal());
                        endereco.setPessoa(finalPessoa);
                        return endereco;
                    })
                    .collect(Collectors.toList());
            enderecoRepository.saveAll(enderecos);
        }

        return new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getDataNascimento(), enderecosDTO);
    }

    /// serviço para deletar, PS: não criei rota pra deleção
    public void deletarPessoa(Long pessoaId){
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        try {
            pessoaRepository.deleteById(pessoa.getId());
        } catch (DataIntegrityViolationException exception) {
            exception.getMessage();
        }
    }
}
