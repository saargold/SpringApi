package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ProductController {
    private final ProductRepo productRepo;
    private final ProductEntityAssembler productEntityAssembler;
    private final ProductDtoAssembler productDtoAssembler;

    public ProductController(ProductRepo productRepo, ProductEntityAssembler productEntityAssembler, ProductDtoAssembler productDtoAssembler) {
        this.productRepo = productRepo;
        this.productEntityAssembler = productEntityAssembler;
        this.productDtoAssembler = productDtoAssembler;
    }
    @GetMapping("/simpleproducts")
    public List<Product> allProductsSimple(){
        return (List<Product>) productRepo.findAll();
    }
    // for internal use
    @GetMapping("/products/{id}")
    EntityModel<Product> getProductById(@PathVariable Long id){
        Product product = productRepo.findById(id).
                orElseThrow(()->new ProductNotFoundException(id));
        return productEntityAssembler.toModel(product);
    }

    @GetMapping("/products")
    CollectionModel<EntityModel<Product>> getAllProducts(){
        List<EntityModel<Product>> products = productRepo.findAll().
                stream().map(product -> productEntityAssembler.toModel(product)).collect(Collectors.toList());
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class)
                .getAllProducts()).withSelfRel());
    }

    @GetMapping("/products/info")
    public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> allProductsInfo(){
        return ResponseEntity.ok(productDtoAssembler.toCollectionModel(
                StreamSupport.stream(productRepo.findAll().spliterator(),false)
                        .map(ProductDTO::new).collect(Collectors.toList())));
    }

    @GetMapping("/products/{id}/info")
    public ResponseEntity<EntityModel<ProductDTO>> singleProductInfo(@PathVariable long id){
        return productRepo.findById(id)
                .map(ProductDTO::new)
                .map(productDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/products/byname/{name}")
    ResponseEntity<?> getProductById(@PathVariable String name){
        List<EntityModel<Product>> products = productRepo.getProductsByTitle(name)
                .stream().map(productEntityAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok().body(CollectionModel.of(products));
    }

    @GetMapping("products/category")
    CollectionModel<EntityModel<Product>> getProductsByCategory(@RequestParam(required = true)  String category){
        List<EntityModel<Product>> products =  productRepo.getProductsByCategory(category).stream()
                .map(productEntityAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());

    }
}
