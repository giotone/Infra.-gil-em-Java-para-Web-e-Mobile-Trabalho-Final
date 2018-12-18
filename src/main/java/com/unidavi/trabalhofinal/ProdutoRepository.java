package com.unidavi.trabalhofinal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ProdutoRepository
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContaining(String nome);

    @Query("SELECT a FROM Produto a WHERE a.nome = :nome")
	List<Produto> findByDataNascimentoAtMesCorrente(@Param("nome") Integer mes);
    
}