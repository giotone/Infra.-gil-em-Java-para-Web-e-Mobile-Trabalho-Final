package com.unidavi.trabalhofinal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ProdutoRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProdutoRepository repository;

    @Test
    public void testSaveProduto() throws Exception {
        Produto produto = Produto.builder()
            .nome("Banco pesca")            
            .descricao("Banco para pescaria")
            .marca("Tramontina")
            .valor(9.99).build();

        produto = repository.save(produto);

        assertNotNull(produto);
        assertTrue(produto.getId() != null);        
    }

    @Test
	public void testDeleteProduto() throws Exception {
		Produto produto = entityManager.persist(Produto.builder()
            .nome("Banco pesca")            
            .descricao("Banco para pescaria")
            .marca("Tramontina")
            .valor(9.99).build());
		
		repository.delete(produto);		
		produto = repository.findOne(produto.getId());
		
		assertNull(produto);
    }
    
    @Test
	public void testFindByMarca() throws Exception {
		entityManager.persistAndFlush(Produto.builder()
            .nome("Banco pesca")            
            .descricao("Banco para pescaria")
            .marca("Tramontina")
            .valor(9.99).build());
		
		List<Produto> produtos = repository.findByMarca("Tramontina");
		
		assertNotNull(produtos);
		assertFalse(produtos.isEmpty());
		assertTrue(produtos.get(0).getMarca().equals("Tramontina"));
	}

}