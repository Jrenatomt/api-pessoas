package com.pessoas.service;

import com.pessoas.dto.EnderecoDTO;
import com.pessoas.entidades.Endereco;
import com.pessoas.entidades.Pessoa;
import com.pessoas.repositories.EnderecoRepository;
import com.pessoas.repositories.PessoaRepository;
import com.pessoas.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    //serviço que Lista endereços de uma pessoa
    @Transactional(readOnly = true)
    public List<EnderecoDTO> listaEnderecosPessoa(Long pessoaId){
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        List<Endereco> enderecos = enderecoRepository.findByPessoa(pessoa);
        return enderecos.stream().map(EnderecoDTO::new).collect(Collectors.toList());
    }

    //serviço para adicionar endereço
    @Transactional
    public EnderecoDTO criarEndereco(Long pessoaId, EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        Endereco endereco = getEnderecoDtoParaEntidade(enderecoDTO, pessoa);
        endereco = enderecoRepository.save(endereco);
        return new EnderecoDTO(endereco);
    }

    //serviço que marca endereço como principal
    @Transactional
    public EnderecoDTO marcarEnderecoPrincipal(Long pessoaId, Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        Endereco endereco = pessoa.getEnderecos().stream().filter(e -> e.getId().equals(enderecoId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + enderecoId));
        pessoa.getEnderecos().forEach(e -> e.setPrincipal(false));
        endereco.setPrincipal(true);
        enderecoRepository.save(endereco);
        return new EnderecoDTO(endereco);
    }

    // serviço para editar endereço
    public EnderecoDTO editarEndereco(Long pessoaId, Long enderecoId, EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        Endereco endereco = pessoa.getEnderecos().stream().filter(e -> e.getId().equals(enderecoId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + enderecoId));
        endereco.setRua(enderecoDTO.getRua());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setPrincipal(enderecoDTO.isPrincipal());
        endereco.setPessoa(pessoa);
        enderecoRepository.save(endereco);
        return new EnderecoDTO(endereco);
    }

    //metodo auxiliar pra copiar do DTO para entidade
    private Endereco getEnderecoDtoParaEntidade(EnderecoDTO enderecoDTO, Pessoa pessoa) {
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.getRua());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setPrincipal(enderecoDTO.isPrincipal());
        endereco.setPessoa(pessoa);
        return endereco;
    }
}
