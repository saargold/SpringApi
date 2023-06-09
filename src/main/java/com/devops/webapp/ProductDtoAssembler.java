package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductDtoAssembler implements SimpleRepresentationModelAssembler<ProductDTO> {



    @Override
    public void addLinks(EntityModel<ProductDTO> resource) {
        resource.add(linkTo(methodOn(ProductController.class)
                .singleProductInfo(resource.getContent().getProduct().getId())).withSelfRel());
        resource.add(linkTo(methodOn(ProductController.class)
                .allProductsInfo())
                .withRel("product information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ProductDTO>> resources) {
        resources.add(linkTo(methodOn(ProductController.class).allProductsInfo()).withSelfRel());
    }
}



