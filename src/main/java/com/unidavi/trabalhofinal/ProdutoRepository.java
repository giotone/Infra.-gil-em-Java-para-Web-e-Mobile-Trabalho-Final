package com.unidavi.trabalhofinal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ProdutoRepository
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContaining(String nome);

    List<Produto> findByMarca(String marca);

    // @Query("SELECT c FROM Cliente c, Endereco e WHERE c.id = e.clienteId and e.rua LIKE :rua")
    @Query("SELECT pr FROM Produto pr, Pedido p, Item i WHERE i.produtoId = pr.id and i.pedidoNumero = p.numero and TO_CHAR(p.dataCriacao,'YYYY-MM-DD') = :data")
    List<Produto> findByDataCriacao(@Param("data") String data);

}