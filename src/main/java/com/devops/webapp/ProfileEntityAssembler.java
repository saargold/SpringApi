package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

/**
 * This class extends SimpleIdentifiableRepresentationModelAssembler in order to assemble an EntityModel of
 * a profile from a Profile object
 * SimpleIdentifiableRepresentationModelAssembler requires at least 2 things:
 * 1. Specify the concrete type to assemble as EntityModel
 * 2. at least 1 constructor matching super. We will use the constructor that specifies in which controller
 * EntityModel<Profile> objects are used
 * EntityModel<Profile> represents a Profile object including the posts it holds
 */
@Component
public class ProfileEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Profile>{
    public ProfileEntityAssembler() {
        super(ProfileController.class);
    }

    @Override
    public void addLinks(EntityModel<Profile> resource) {
        super.addLinks(resource);
        Optional<Long> tempid = Optional.ofNullable(resource.getContent().getId());
        tempid.ifPresent(id -> { //
            // Add custom link to find all managed posts
            resource.add(linkTo(methodOn(PostController.class).postsByProfile(id)).withRel("posts"));
        });
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Profile>> resources) {
        super.addLinks(resources);
        resources.add(linkTo(methodOn(PostController.class).allPosts()).withRel("posts"));
    }
}
