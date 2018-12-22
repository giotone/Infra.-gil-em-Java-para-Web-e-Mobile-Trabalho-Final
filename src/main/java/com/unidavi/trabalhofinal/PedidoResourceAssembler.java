package com.unidavi.trabalhofinal;

/**
 * PedidoResourceAssembler
 */
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class PedidoResourceAssembler extends ResourceAssemblerSupport<Pedido, PedidoResource> {
	
	public PedidoResourceAssembler() {
		super(Pedido.class, PedidoResource.class);
	}

	@Override
	public PedidoResource toResource(Pedido pedido) {
		return new PedidoResource(pedido, linkTo(methodOn(PedidoRestController.class).get(pedido.getNumero())).withSelfRel());
	}
	
	@Override
	protected PedidoResource instantiateResource(Pedido pedido) {
		return new PedidoResource(pedido);
	}

}