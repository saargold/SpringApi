package com.devops.webapp;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController

public class OrderController {
    private final OrderRepo orderRepo;
    private final OrderEntityAssembler orderEntityAssembler;

    private final CustomerRepo customerRepo;

    public OrderController(OrderRepo orderRepo, OrderEntityAssembler orderEntityAssembler, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.orderEntityAssembler = orderEntityAssembler;
        this.customerRepo = customerRepo;
    }
    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> getAllOrders() {
        List<EntityModel<Order>> orders = orderRepo.findAll().
                stream().map(order -> orderEntityAssembler.toModel(order)).collect(Collectors.toList());
        return CollectionModel.of(orders, linkTo(methodOn(ProductController.class)
                .getAllProducts()).withSelfRel());
    }

    @GetMapping("/orders/user")
    CollectionModel<EntityModel<Order>> getOrdersByUser(@RequestBody Customer storeUser) {
        List<EntityModel<Order>> orders = orderRepo.getStoreOrdersByCustomer(storeUser).
                stream().map(order -> orderEntityAssembler.toModel(order)).collect(Collectors.toList());
        return CollectionModel.of(orders, linkTo(methodOn(ProductController.class)
                .getAllProducts()).withSelfRel());
    }

    @GetMapping("/orders/id")
    EntityModel<Order> getOrderById(@PathVariable Long id) {
        Order storeOrder = orderRepo.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id));
        return orderEntityAssembler.toModel(storeOrder);
    }

    @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> createOrder(@RequestBody(required = true) Product product, @RequestBody Long id) {
        List<Product> products = new ArrayList<>();
        products.add(product);
        Customer storeUser = customerRepo.findById(id).orElseThrow(()-> new RuntimeException("user doesnt exist"));
        Order storeOrder = orderRepo.save(new Order(products, storeUser));
        return ResponseEntity.created(linkTo(methodOn(OrderController.class).
                getOrderById(storeOrder.getId())).toUri()).body(orderEntityAssembler.toModel(storeOrder));

    }

    @PutMapping("orders/{id}")
    ResponseEntity<?> addProductToOrder(@PathVariable Long id, @RequestBody Product product){
        Order storeOrder = orderRepo.findById(id).map(orderToUpdate ->{
            orderToUpdate.addProduct(product);
            return orderRepo.save(orderToUpdate);}).orElseThrow(()-> new OrderNotFoundException(id));
        return ResponseEntity.created(linkTo(methodOn(OrderController.class).
                getOrderById(storeOrder.getId())).toUri()).body(orderEntityAssembler.toModel(storeOrder));
    }

    @DeleteMapping("orders/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderRepo.delete(orderRepo.findById(id).orElseThrow(()->new OrderNotFoundException(id)));
        return ResponseEntity.ok("order with id: " + id+ " was deleted");
    }
}
