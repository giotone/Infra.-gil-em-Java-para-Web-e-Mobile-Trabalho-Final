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
 * EnderecoRestController
 */
@RestController
@RequestMapping("/enderecos")
public class EnderecoRestController {

    @Autowired
	EnderecoRepository repository;
	
	EnderecoResourceAssembler assembler = new EnderecoResourceAssembler();
	
	@PostConstruct
	public void init() {
		repository.save(new Endereco(1l, "XV de Novembro", "Rio do Sul", "SC", "89160-033", 2l));
		repository.save(new Endereco(2l, "Ribeirão Basilio", "Laurentino", "SC", "89170-000", 1l));
		repository.save(new Endereco(3l, "Av. Oscar Barcelos", "Rio do Sul", "SC", "89160-314", 3l));
		
	}
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna toda a lista de enderecos sem filtro")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<EnderecoResource>> getAll() {
		return new ResponseEntity<>(assembler.toResources(repository.findAll()), HttpStatus.OK);
	}
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de um endereco específico buscando pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<EnderecoResource> get(@PathVariable Long id) {
		Endereco endereco = repository.findOne(id);
		if (endereco != null) {			
			return new ResponseEntity<>(assembler.toResource(endereco), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de um endereco específico buscando pelo conteudo do clienteId")
    @GetMapping("/cliente/{cliente}")
	public ResponseEntity<List<EnderecoResource>> findByClienteId(@PathVariable Long cliente) {
		return new ResponseEntity<>(assembler.toResources(repository.findByClienteId(cliente)), HttpStatus.OK);
	}
    
    @Secured("ROLE_MANAGER")
    @ApiOperation("Adiciona um novo endereco")
	@PostMapping
	public ResponseEntity<EnderecoResource> create(@RequestBody Endereco endereco) {
		endereco = repository.save(endereco);
		if (endereco != null) {
			return new ResponseEntity<>(assembler.toResource(endereco), HttpStatus.OK);					
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	@Secured("ROLE_MANAGER")
    @ApiOperation("altera um endereco existente com base no id")
	@PutMapping("/{id}")
	public ResponseEntity<EnderecoResource> update(@PathVariable Long id, @RequestBody Endereco endereco) {
		if (endereco != null) {
			endereco.setId(id);
			endereco = repository.save(endereco);
			return new ResponseEntity<>(assembler.toResource(endereco), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
        
    @Secured("ROLE_MANAGER")
    @ApiOperation("apaga um endereco existente com base no id")
	@DeleteMapping("/{id}")
	public ResponseEntity<EnderecoResource> delete(@PathVariable Long id) {
		Endereco endereco = repository.findOne(id);
		if (endereco != null) {
			repository.delete(endereco);
			return new ResponseEntity<>(assembler.toResource(endereco), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}