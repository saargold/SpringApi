package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class PostController {
    private final PostRepo postRepo;
    private final PostEntityAssembler postEntityAssembler;
    private final PostDtoAssembler postDtoAssembler;

    public PostController(PostRepo postRepo, PostEntityAssembler postEntityAssembler
            ,PostDtoAssembler postDtoAssembler) {
        this.postRepo = postRepo;
        this.postEntityAssembler = postEntityAssembler;
        this.postDtoAssembler = postDtoAssembler;
    }

    @GetMapping("/simpleposts")
    public List<Post> allPostsSimple(){
        return (List<Post>) postRepo.findAll();
    }
    // for internal use
    @GetMapping("/posts")
    public ResponseEntity<CollectionModel<EntityModel<Post>>> allPosts(){
            return ResponseEntity.ok(postEntityAssembler.toCollectionModel(postRepo.findAll()));
        }
    @GetMapping("/posts/info")
    public ResponseEntity<CollectionModel<EntityModel<PostDTO>>> allPostsInfo(){
        return ResponseEntity.ok(postDtoAssembler.toCollectionModel(
                StreamSupport.stream(postRepo.findAll().spliterator(),false)
                        .map(PostDTO::new).collect(Collectors.toList())));
    }

    @GetMapping("/posts/{id}/info")
    public ResponseEntity<EntityModel<PostDTO>> singlePostInfo(@PathVariable long id){
        return postRepo.findById(id)
                .map(PostDTO::new)
                .map(postDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/posts/{id}")
    public ResponseEntity<EntityModel<Post>> singlePost(@PathVariable long id) {
        return postRepo.findById(id) //
                .map(postEntityAssembler::toModel) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/posts/byprofile/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Post>>> postsByProfile(@PathVariable Long id){
        return ResponseEntity.ok(postEntityAssembler.toCollectionModel(postRepo.findByProfileId(id)));
    }
}
