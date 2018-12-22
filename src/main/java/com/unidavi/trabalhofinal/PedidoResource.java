package com.unidavi.trabalhofinal;

/**
 * PedidoResource
 */
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class PedidoResource extends Resource<Pedido> {
	
	public PedidoResource(Pedido pedido, Link... links) {
		super(pedido, links);
	}
	
}