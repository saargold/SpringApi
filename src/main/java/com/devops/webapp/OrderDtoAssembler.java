package com.devops.webapp;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDtoAssembler implements SimpleRepresentationModelAssembler<OrderDTO> {

@Override
public void addLinks(EntityModel<OrderDTO> resource) {
        resource.add(linkTo(methodOn(OrderController.class)
        .singleOrderInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(OrderController.class)
        .allOrderInfo())
        .withRel("post information"));
        }

@Override
public void addLinks(CollectionModel<EntityModel<OrderDTO>> resources) {
        resources.add(linkTo(methodOn(OrderController.class).allOrderInfo()).withSelfRel());
        }
        }

