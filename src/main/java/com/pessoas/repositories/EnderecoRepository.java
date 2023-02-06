package com.pessoas.repositories;

import com.pessoas.dto.EnderecoDTO;
import com.pessoas.entidades.Endereco;
import com.pessoas.entidades.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByPessoa(Pessoa pessoa);
}
