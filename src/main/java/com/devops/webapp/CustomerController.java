package com.devops.webapp;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {
    private final CustomerRepo customerRepo;
    private final CustomerEntityAssembler customerEntityAssembler;

    public CustomerController(CustomerRepo customerRepo,CustomerEntityAssembler customerEntityAssembler){
        this.customerRepo=customerRepo;
        this.customerEntityAssembler=customerEntityAssembler;
    }
    @GetMapping("/users/{id}")
    EntityModel<Customer> getUserById(@PathVariable Long id){
        Customer storeUser = customerRepo.findById(id).
                orElseThrow(()->new NoSuchElementException());
        return customerEntityAssembler.toModel(storeUser);
    }

    @GetMapping("/users")
    CollectionModel<EntityModel<Customer>> getAllUsers(){
        List<EntityModel<Customer>> users = customerRepo.findAll().
                stream().map(user -> customerEntityAssembler.toModel(user)).collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(CustomerController.class)
                .getAllUsers()).withSelfRel());
    }

    @PostMapping("/users")
    ResponseEntity<EntityModel<Customer>> createUser(@RequestBody Customer newStoreUser){
        Customer savedStoreUser = customerRepo.save(newStoreUser);
        return ResponseEntity.created(linkTo(methodOn(CustomerController.class).
                getUserById(savedStoreUser.getCustomerId())).toUri()).body(customerEntityAssembler.toModel(savedStoreUser));
    }
}
