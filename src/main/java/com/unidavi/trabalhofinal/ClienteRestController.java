package com.unidavi.trabalhofinal;

import java.util.Calendar;
import java.util.Date;
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

/**
 * ClienteRestController
 */
@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    @Autowired
	ClienteRepository repository;
	
	ClienteResourceAssembler assembler = new ClienteResourceAssembler();
	
	@PostConstruct
	public void init() {
		repository.save(new Cliente(1l, "Giovani", "giovani@gmail.com", "123.456.789-01", new Date()));
		repository.save(new Cliente(2l, "José", "jose@gmail.com", "123.456.789-01", new Date()));
		repository.save(new Cliente(3l, "Maria", "maria@gmail.com", "123.456.789-01", new Date()));
		repository.save(new Cliente(4l, "João", "joao@gmail.com", "123.456.789-01", new Date()));
        
        // Long id;
        // String nome;
        // String email;
        // String cpf;
        // Date dataNascimento;
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ClienteResource>> getAll() {
		return new ResponseEntity<>(assembler.toResources(repository.findAll()), HttpStatus.OK);
	}
    
    @Secured("ROLE_USER")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResource> get(@PathVariable Long id) {
		Cliente cliente = repository.findOne(id);
		if (cliente != null) {			
			return new ResponseEntity<>(assembler.toResource(cliente), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    @GetMapping("/nome/{nome}")
	public ResponseEntity<List<ClienteResource>> findByNome(@PathVariable String nome) {
		return new ResponseEntity<>(assembler.toResources(repository.findByNomeContaining(nome)), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ClienteResource> create(@RequestBody Cliente cliente) {
		cliente = repository.save(cliente);
		if (cliente != null) {
			return new ResponseEntity<>(assembler.toResource(cliente), HttpStatus.OK);					
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteResource> update(@PathVariable Long id, @RequestBody Cliente cliente) {
		if (cliente != null) {
			cliente.setId(id);
			cliente = repository.save(cliente);
			return new ResponseEntity<>(assembler.toResource(cliente), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
        
    @Secured("ROLE_MANAGER")
	@DeleteMapping("/{id}")
	public ResponseEntity<ClienteResource> delete(@PathVariable Long id) {
		Cliente cliente = repository.findOne(id);
		if (cliente != null) {
			repository.delete(cliente);
			return new ResponseEntity<>(assembler.toResource(cliente), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
}