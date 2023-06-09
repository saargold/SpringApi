package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;
import java.util.Optional;

@Component

public class ProductEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Product> {


    public ProductEntityAssembler() {
        super(ProductController.class);
    }


    @Override
    public void addLinks(EntityModel<Product> resource) {
        resource.add(linkTo(methodOn(ProductController.class)
                .getProductById(Objects.requireNonNull(resource.getContent()).getId())).withSelfRel());
        resource.add(linkTo(methodOn(ProductController.class)
                .getAllProducts())
                .withRel("back to all products"));

    }


}