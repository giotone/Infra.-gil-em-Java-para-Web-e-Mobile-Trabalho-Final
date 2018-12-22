package com.unidavi.trabalhofinal;

/**
 * EnderecoResourceAssembler
 */
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class EnderecoResourceAssembler extends ResourceAssemblerSupport<Endereco, EnderecoResource> {
	
	public EnderecoResourceAssembler() {
		super(Endereco.class, EnderecoResource.class);
	}

	@Override
	public EnderecoResource toResource(Endereco endereco) {
		return new EnderecoResource(endereco, linkTo(methodOn(EnderecoRestController.class).get(endereco.getId())).withSelfRel());
	}
	
	@Override
	protected EnderecoResource instantiateResource(Endereco endereco) {
		return new EnderecoResource(endereco);
	}

}