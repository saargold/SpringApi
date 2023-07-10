package com.devops.webapp;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController

public class UserController {

    private final UserRepo userRepo;
    private final UserEntityAssembler userEntityAssembler;
    private final UserDtoAssembler userDtoAssembler;
    /**
     * Constructs a UserController with the given repository and assemblers.
     *
     * @param userRepo the repository to manage User
     * @param userEntityAssembler assembler to convert User to entity model
     * @param userDtoAssembler assembler to convert User to DTO
     */
    public UserController(UserRepo repo, UserEntityAssembler userEntityAssembler ,UserDtoAssembler userDtoAssembler){
        this.userRepo = repo;
        this.userEntityAssembler = userEntityAssembler;
        this.userDtoAssembler=userDtoAssembler;
    }
    @GetMapping("/users/{id}")
    EntityModel<User> getUserById(@PathVariable Long id){
        User storeUser = userRepo.findById(id).
                orElseThrow(()->new UserNotFoundException(id));
        return userEntityAssembler.toModel(storeUser);
    }
    /**
     * Returns the user identified by the given id as a DTO.
     *
     * @param id the id of the user to find
     * @return a response entity containing a DTO of the user identified by the id
     */

    @GetMapping("/users/{id}/info")
    public ResponseEntity<EntityModel<UserDTO>> singleUserInfo(@PathVariable long id){
        return userRepo.findById(id)
                .map(UserDTO::new)
                .map(userDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Returns all users as a collection of DTOs.
     *
     * @return a response entity containing a collection of DTOs representing all users
     */
    @GetMapping("/users/info")
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> allUsersInfo(){
        return ResponseEntity.ok(userDtoAssembler.toCollectionModel(
                StreamSupport.stream(userRepo.findAll().spliterator(),false)
                        .map(UserDTO::new).collect(Collectors.toList())));
    }

    /**
     * Returns all users as a collection of entity models.
     *
     * @return a collection of entity models representing all users
     */
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> getAllUsers(){
        List<EntityModel<User>> users = userRepo.findAll().
                stream().map(user -> userEntityAssembler.toModel(user)).collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class)
                .getAllUsers()).withSelfRel());
    }
    /**
     * Updates the orders of the user identified by the given id.
     *
     * @param id the id of the user to update
     * @param order the new list of orders
     * @return a response entity containing an entity model of the updated user
     */
@PutMapping("/users/{id}")
ResponseEntity<EntityModel<User>> updateName(@PathVariable Long id, @RequestBody(required = true)  List<Order> order){
    EntityModel<User> product = userRepo.findById(id).map(productToUpdate->{
        productToUpdate.setOrders(order);
        return userRepo.save(productToUpdate);
    }).map(userEntityAssembler::toModel).orElseThrow(()->new ProductNotFoundException(id));
    return ResponseEntity.accepted().body(product);
}

    /**
     * Creates a new user.
     *
     * @param newStoreUser the user to create
     * @return a response entity containing an entity model of the created user
     */
    @PostMapping("/users")
    ResponseEntity<EntityModel<User>> createUser(@RequestBody User newStoreUser){
        User savedStoreUser = userRepo.save(newStoreUser);
        return ResponseEntity.created(linkTo(methodOn(UserController.class).
                getUserById(savedStoreUser.getId())).toUri()).body(userEntityAssembler.toModel(savedStoreUser));
    }
    /**
     * Deletes the user identified by the given id.
     *
     * @param id the id of the user to delete
     * @return a response entity confirming the deletion of the user
     */
    @DeleteMapping("users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id){
        System.out.println(id + "this is id");
        userRepo.delete(userRepo.findById(id).orElseThrow(()->new UserNotFoundException(id)));
        return ResponseEntity.ok("Deleted User id: " + id+ " success");
    }



}
