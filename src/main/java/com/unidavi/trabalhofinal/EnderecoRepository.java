package com.unidavi.trabalhofinal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EnderecoRepository
 */
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByClienteId(Long clienteId);
    
}