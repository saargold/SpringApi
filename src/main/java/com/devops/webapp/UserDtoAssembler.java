package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component

public class UserDtoAssembler implements SimpleRepresentationModelAssembler<UserDTO>{
    @Override
    public void addLinks(EntityModel<UserDTO> resource) {
        resource.add(linkTo(methodOn(UserController.class)
                .singleUserInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(UserController.class)
                .allUsersInfo())
                .withRel("product information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<UserDTO>> resources) {
        resources.add(linkTo(methodOn(UserController.class).allUsersInfo()).withSelfRel());
    }
}


