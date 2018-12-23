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
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * ItemRestController
 */
@RestController
@RequestMapping("/itens")
public class ItemRestController {

    @Autowired
    ItemRepository repository;

    ItemResourceAssembler assembler = new ItemResourceAssembler();

    @PostConstruct
    public void init() {
        repository.save(new Item(1l, 1l, 2, 25.00));
        repository.save(new Item(2l, 1l, 4, 25.00));

        // Long pedidoNumero;
        // Long produtoId;
        // Integer quantidade;
        // Double total;
    }

    @Secured("ROLE_USER")
    @ApiOperation("Retorna toda a lista de itens sem filtro")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<ItemResource>> getAll() {
        return new ResponseEntity<>(assembler.toResources(repository.findAll()), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @ApiOperation("Retorna os dados de itens específico buscando pelo numero do pedido")
    @GetMapping("/{numero}")
    public ResponseEntity<ItemResource> get(@PathVariable Long numero) {
        Item item = repository.findOne(numero);
        if (item != null) {
            return new ResponseEntity<>(assembler.toResource(item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_MANAGER")
    @ApiOperation("Adiciona um novo item")
    @PostMapping
    public ResponseEntity<ItemResource> create(@RequestBody Item item) {
        item = repository.save(item);
        if (item != null) {
            return new ResponseEntity<>(assembler.toResource(item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    // @Secured("ROLE_MANAGER")
    // @ApiOperation("altera um item existente com base no numero")
    // @PutMapping("/{numero}/{produto}")
    // public ResponseEntity<ItemResource> update(@PathVariable Long numero, @PathVariable Long produto, @RequestBody Item item) {
    //     if (item != null) {
    //         item.setPedidoNumero(numero);
    //         item.setProdutoId(produto);
    //         item = repository.save(item);
    //         return new ResponseEntity<>(assembler.toResource(item), HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    //     }
    // }

    @Secured("ROLE_MANAGER")
    @ApiOperation("apaga um item existente com base no numero")
    @DeleteMapping("/{numero}")
    public ResponseEntity<ItemResource> delete(@PathVariable Long numero) {
        Item item = repository.findOne(numero);
        if (item != null) {
            repository.delete(item);
            return new ResponseEntity<>(assembler.toResource(item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}