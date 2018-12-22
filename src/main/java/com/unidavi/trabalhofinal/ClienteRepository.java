package com.unidavi.trabalhofinal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

/**
 * ClienteRepository
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContaining(String nome);

    // @Query("SELECT a FROM Cliente a WHERE a.nome = :nome")
	// List<Cliente> findByDataNascimentoAtMesCorrente(@Param("nome") Integer mes);
    
}