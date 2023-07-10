package com.devops.webapp;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
public class OrderController {

        private final OrderRepo orderRepo;
        private final OrderEntityAssembler orderEntityAssembler;
        private final OrderDtoAssembler orderDtoAssembler;

    private final UserRepo userRepo;

        public OrderController(OrderRepo orderRepo, OrderEntityAssembler orderEntityAssembler, OrderDtoAssembler orderDtoAssembler, UserRepo userRepo) {
            this.orderRepo = orderRepo;
            this.orderEntityAssembler = orderEntityAssembler;
            this.orderDtoAssembler=orderDtoAssembler;
            this.userRepo = userRepo;
        }

    /**
     * Fetches all orders in a simple format.
     *
     * @return a list of all orders
     */
    @GetMapping("/simpleorders")
    public List<Order> allOrderSimple(){
        return (List<Order>) orderRepo.findAll();
    }

    /**
     * Fetches all orders with additional hypermedia links.
     *
     * @return a collection model of all orders with self-reference links
     */
    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> getAllOrders() {
        List<EntityModel<Order>> orders = orderRepo.findAll().
                stream().map(order -> orderEntityAssembler.toModel(order)).collect(Collectors.toList());
        return CollectionModel.of(orders, linkTo(methodOn(ProductController.class)
                .getAllProducts()).withSelfRel());
    }





    /**
     * Fetches a specific order's detailed information.
     *
     * @param id the id of the order to fetch
     * @return a response entity containing an entity model of the fetched order
     */
    @GetMapping("/orders/{id}/info")
    public ResponseEntity<EntityModel<OrderDTO>> singleOrderInfo(@PathVariable long id){
        return orderRepo.findById(id)
                .map(OrderDTO::new)
                .map(orderDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Fetches the detailed information of all orders.
     *
     * @return a response entity containing a collection model of all orders
     */
    @GetMapping("/orders/info")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> allOrderInfo(){
        return ResponseEntity.ok(orderDtoAssembler.toCollectionModel(
                StreamSupport.stream(orderRepo.findAll().spliterator(),false)
                        .map(OrderDTO::new).collect(Collectors.toList())));
    }

    /**
     * Fetches a specific order.
     *
     * @param id the id of the order to fetch
     * @return a response entity containing an entity model of the fetched order
     */
    @GetMapping("/orders/{id}")
    ResponseEntity<EntityModel<Order>> getByOrderId(@PathVariable long id){
        return orderRepo.findById(id)
                .map(orderEntityAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Creates a new order for a specific user.
     *
     * @param product the list of products to add to the order
     * @param userId the id of the user to create the order for
     * @return a response entity containing an entity model of the created order
     */
    @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> createOrder(@RequestBody(required = true) List<Product> product,@RequestParam long userId) {
       // List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        User storeUser = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("user doesnt exist"));
        Order order =  orderRepo.save(new Order(product,storeUser));
       orders.add(order);
       storeUser.addOrder(order);
       userRepo.save(storeUser);



        return ResponseEntity.created(linkTo(methodOn(OrderController.class).
               getByOrderId(order.getId())).toUri()).body(orderEntityAssembler.toModel(order));

    }

//    @PostMapping("/orders")
//    ResponseEntity<EntityModel<Order>> createOrder(@RequestBody(required = true) Product product,@RequestParam long userId) {
//        List<Product> products = new ArrayList<>();
//        products.add(product);
//        User storeUser = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("user doesnt exist"));
//        Order storeOrder = orderRepo.save(new Order(products, storeUser));
//        //System.out.println(storeOrder.getClass());
//
//        return ResponseEntity.created(linkTo(methodOn(OrderController.class).
//                getByOrderId(storeOrder.getId())).toUri()).body(orderEntityAssembler.toModel(storeOrder));
//
//    }
    /**
     * Adds a product to a specific order.
     *
     * @param id the id of the order to add the product to
     * @param product the product to add
     * @return a response entity containing an entity model of the updated order
     */
    @PutMapping("orders/{id}")
    ResponseEntity<?> addProductToOrder(@PathVariable Long id, @RequestBody Product product){
        Order storeOrder = orderRepo.findById(id).map(orderToUpdate ->{
            orderToUpdate.addProduct(product);
            return orderRepo.save(orderToUpdate);}).orElseThrow(()-> new ProductNotFoundException(id));
        return ResponseEntity.created(linkTo(methodOn(OrderController.class).
                getByOrderId(storeOrder.getId())).toUri()).body(orderEntityAssembler.toModel(storeOrder));
    }
    /**
     * Deletes a specific order.
     *
     * @param id the id of the order to delete
     * @return a response entity confirming the deletion of the order
     */
    @DeleteMapping("orders/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderRepo.delete(orderRepo.findById(id).orElseThrow(()->new ProductNotFoundException(id)));
        return ResponseEntity.ok("order with id: " + id+ " was deleted");
    }
    }
