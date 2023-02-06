package com.pessoas.controllers;

import com.pessoas.dto.AtualizaPessoaDTO;
import com.pessoas.dto.EnderecoDTO;
import com.pessoas.dto.PessoaDTO;
import com.pessoas.service.EnderecoService;
import com.pessoas.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodasPessoas(){
        List<PessoaDTO> list = pessoaService.BuscarTodasPessoas();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{pessoaId}")
    public ResponseEntity<PessoaDTO> buscarPessoa (@PathVariable Long pessoaId){
        PessoaDTO pessoaDTO = pessoaService.buscarPeloId(pessoaId);
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaSalva = pessoaService.salvaPessoa(pessoaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pessoaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(pessoaSalva);
    }

    @PutMapping(value = "/{pessoaId}")
    public ResponseEntity<PessoaDTO> atualizarPessoa(@PathVariable Long pessoaId, @Valid @RequestBody AtualizaPessoaDTO atualizaPessoaDTO){
        PessoaDTO pessoaDTO = pessoaService.atualizaPessoa(pessoaId, atualizaPessoaDTO);
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @GetMapping (value = "/{pessoaId}/enderecos")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPessoa(@PathVariable Long pessoaId){
        List<EnderecoDTO> enderecoDTOs = enderecoService.listaEnderecosPessoa(pessoaId);
        return ResponseEntity.ok().body(enderecoDTOs);
    }

    @PostMapping(value = "/{pessoaId}/enderecos")
    public ResponseEntity<EnderecoDTO> criaEndereco(@PathVariable Long pessoaId, @Valid @RequestBody EnderecoDTO endereco) {
        EnderecoDTO novoEndereco = enderecoService.criarEndereco(pessoaId, endereco);
        return ResponseEntity.ok().body(novoEndereco);
    }

    @PutMapping(value = "/{pessoaId}/{enderecoId}")
    public ResponseEntity<EnderecoDTO> marcarEnderecoPrincipal(@PathVariable Long pessoaId, @PathVariable Long enderecoId) {
        EnderecoDTO dto = enderecoService.marcarEnderecoPrincipal(pessoaId, enderecoId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{pessoaId}/{enderecoId}/editar-enderecos")
    public ResponseEntity<EnderecoDTO> editarEndereco(@PathVariable Long pessoaId, @PathVariable Long enderecoId,
                                                      @Valid @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO dto = enderecoService.editarEndereco(pessoaId, enderecoId, enderecoDTO);
        return ResponseEntity.ok().body(dto);
    }
}
