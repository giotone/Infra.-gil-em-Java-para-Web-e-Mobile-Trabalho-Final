package com.unidavi.trabalhofinal;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * ProdutoRestController
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoRestController {

    @Autowired
	ProdutoRepository repository;
	
	ProdutoResourceAssembler assembler = new ProdutoResourceAssembler();
	
	@PostConstruct
	public void init() {
		repository.save(new Produto(1l, "Cadeira", "Cadeira de Praia", "MOR", 50.00));
		repository.save(new Produto(2l, "Banco", "Banco de Praia", "MOR", 20.00));
		repository.save(new Produto(3l, "Mesa", "Mesa de Praia", "MOR", 120.00));
        
        // Long id;
        // String nome;
        // String descricao;
        // String marca;
        // Double valor;
	}
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna toda a lista de produtos sem filtro")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ProdutoResource>> getAll() {
		return new ResponseEntity<>(assembler.toResources(repository.findAll()), HttpStatus.OK);
	}
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de um produto específico buscando pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResource> get(@PathVariable Long id) {
		Produto produto = repository.findOne(id);
		if (produto != null) {			
			return new ResponseEntity<>(assembler.toResource(produto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de um produto específico buscando pelo conteudo do nome")
    @GetMapping("/nome/{nome}")
	public ResponseEntity<List<ProdutoResource>> findByNome(@PathVariable String nome) {
		return new ResponseEntity<>(assembler.toResources(repository.findByNomeContaining(nome)), HttpStatus.OK);
    }
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de um produto específico buscando pela marca")
    @GetMapping("/marca/{marca}")
	public ResponseEntity<List<ProdutoResource>> findByMarca(@PathVariable String marca) {
		return new ResponseEntity<>(assembler.toResources(repository.findByMarca(marca)), HttpStatus.OK);
    }
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de um produto específico buscando pelo conteudo da data de criação do pedido")
    @GetMapping("/datacriacao/{data}")
	public ResponseEntity<List<ProdutoResource>> findByDataCriacao(@PathVariable String data) {
		return new ResponseEntity<>(assembler.toResources(repository.findByDataCriacao(data)), HttpStatus.OK);
    }
    
    @Secured("ROLE_MANAGER")
    @ApiOperation("Adiciona um novo produto")
	@PostMapping
	public ResponseEntity<ProdutoResource> create(@RequestBody Produto produto) {
		produto = repository.save(produto);
		if (produto != null) {
			return new ResponseEntity<>(assembler.toResource(produto), HttpStatus.OK);					
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
    
    @Secured("ROLE_MANAGER")
    @ApiOperation("altera um produto existente com base no id")
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResource> update(@PathVariable Long id, @RequestBody Produto produto) {
		if (produto != null) {
			produto.setId(id);
			produto = repository.save(produto);
			return new ResponseEntity<>(assembler.toResource(produto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
        
    @Secured("ROLE_MANAGER")
    @ApiOperation("apaga um produto existente com base no id")
	@DeleteMapping("/{id}")
	public ResponseEntity<ProdutoResource> delete(@PathVariable Long id) {
		Produto produto = repository.findOne(id);
		if (produto != null) {
			repository.delete(produto);
			return new ResponseEntity<>(assembler.toResource(produto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}