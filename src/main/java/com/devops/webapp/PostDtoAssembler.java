package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class PostDtoAssembler implements SimpleRepresentationModelAssembler<PostDTO> {

    @Override
    public void addLinks(EntityModel<PostDTO> resource) {
        resource.add(linkTo(methodOn(PostController.class)
                .singlePostInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(PostController.class)
                .allPostsInfo())
                .withRel("post information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<PostDTO>> resources) {
        resources.add(linkTo(methodOn(PostController.class).allPostsInfo()).withSelfRel());
    }
}

