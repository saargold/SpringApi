package com.devops.webapp;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Order> {
    public OrderEntityAssembler() {
        super(OrderController.class);
    }


    @Override
    public void addLinks(EntityModel<Order> resource) {
        resource.add(linkTo(methodOn(OrderController.class)
                .getByOrderId(Objects.requireNonNull(resource.getContent()).getId())).withSelfRel());
        resource.add(linkTo(methodOn(OrderController.class)
                .getAllOrders())
                .withRel("back to all products"));

    }

}
