package com.devops.webapp;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<User> {
    public UserEntityAssembler() {
        super(UserController.class);
    }


    @Override
    public void addLinks(EntityModel<User> resource) {
        resource.add(linkTo(methodOn(UserController.class)
                .getUserById(Objects.requireNonNull(resource.getContent()).getId())).withSelfRel());
        resource.add(linkTo(methodOn(UserController.class)
                .getAllUsers())
                .withRel("back to all products"));

    }

}
