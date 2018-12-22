package com.unidavi.trabalhofinal;

/**
 * ClienteResource
 */
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ClienteResource extends Resource<Cliente> {
	
	public ClienteResource(Cliente cliente, Link... links) {
		super(cliente, links);
	}
	
}