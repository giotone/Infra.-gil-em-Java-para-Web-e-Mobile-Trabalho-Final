package com.unidavi.trabalhofinal;

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

import io.swagger.annotations.ApiOperation;

/**
 * PedidoRestController
 */
@RestController
@RequestMapping("/pedidos")
public class PedidoRestController {

    @Autowired
    PedidoRepository repository;

    PedidoResourceAssembler assembler = new PedidoResourceAssembler();

    @PostConstruct
    public void init() {
        repository.save(new Pedido(1l, 50.00, new Date(), 1l));
        repository.save(new Pedido(2l, 100.00, new Date(), 1l));

        // Long numero;
        // Double total;
        // Date dataCriacao;
        // Long clienteId;
    }

    @Secured("ROLE_USER")
    @ApiOperation("Retorna toda a lista de pedidos sem filtro")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<PedidoResource>> getAll() {
        return new ResponseEntity<>(assembler.toResources(repository.findAll()), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de um pedido específico buscando pelo numero")
    @GetMapping("/{numero}")
    public ResponseEntity<PedidoResource> get(@PathVariable Long numero) {
        Pedido pedido = repository.findOne(numero);
        if (pedido != null) {
            return new ResponseEntity<>(assembler.toResource(pedido), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }    

    // @Secured("ROLE_USER")
    // @ApiOperation("Retorna os dados de um pedido específico buscando pelo conteudo da data de criação")
    // @GetMapping("/data/{data}")
	// public ResponseEntity<List<PedidoResource>> findByDataCriacao(@PathVariable String data) {
	// 	return new ResponseEntity<>(assembler.toResources(repository.findByDataCriacao(data)), HttpStatus.OK);
    // }

    @Secured("ROLE_MANAGER")
    @ApiOperation("Adiciona um novo pedido")
    @PostMapping
    public ResponseEntity<PedidoResource> create(@RequestBody Pedido pedido) {
        pedido = repository.save(pedido);
        if (pedido != null) {
            return new ResponseEntity<>(assembler.toResource(pedido), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Secured("ROLE_MANAGER")
    @ApiOperation("altera um pedido existente com base no numero")
    @PutMapping("/{numero}")
    public ResponseEntity<PedidoResource> update(@PathVariable Long numero, @RequestBody Pedido pedido) {
        if (pedido != null) {
            pedido.setNumero(numero);
            pedido = repository.save(pedido);
            return new ResponseEntity<>(assembler.toResource(pedido), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Secured("ROLE_MANAGER")
    @ApiOperation("apaga um pedido existente com base no numero")
    @DeleteMapping("/{numero}")
    public ResponseEntity<PedidoResource> delete(@PathVariable Long numero) {
        Pedido pedido = repository.findOne(numero);
        if (pedido != null) {
            repository.delete(pedido);
            return new ResponseEntity<>(assembler.toResource(pedido), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}