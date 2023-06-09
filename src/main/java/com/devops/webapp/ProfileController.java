
package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    private final ProfileRepo profileRepo;
    private final ProfileEntityAssembler profileAssembler;

    public ProfileController(ProfileRepo profileRepo, ProfileEntityAssembler profileAssembler) {
        this.profileRepo = profileRepo;
        this.profileAssembler = profileAssembler;
    }

    @GetMapping("/profiles/{id}")
    ResponseEntity<EntityModel<Profile>> singleProfile(@PathVariable long id){
        return profileRepo.findById(id)
                .map(profileAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/profiles")
    ResponseEntity<CollectionModel<EntityModel<Profile>>> allProfiles(){
        return ResponseEntity.ok(profileAssembler.toCollectionModel(profileRepo.findAll()));
    }

}

