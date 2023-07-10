package com.devops.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Constructs a ProductController with the given repository and assemblers.
     *
     * @param productRepo the repository to manage products
     * @param productEntityAssembler assembler to convert product to entity model
     * @param productDtoAssembler assembler to convert product to DTO
     */

    public ProductController(ProductRepo productRepo, ProductEntityAssembler productEntityAssembler, ProductDtoAssembler productDtoAssembler) {
        this.productRepo = productRepo;
        this.productEntityAssembler = productEntityAssembler;
        this.productDtoAssembler = productDtoAssembler;
    }
    //GET
    /**
     * Returns a list of all products.
     *
     * @return a list of products
     */
    @GetMapping("/simpleproducts")
    public List<Product> allProductsSimple(){
        return (List<Product>) productRepo.findAll();
    }
    // for internal use

    /**
     * Returns the product identified by the given id.
     *
     * @param id the id of the product to find
     * @return the product identified by the id
     */
    @GetMapping("/products/{id}")
    EntityModel<Product> getProductById(@PathVariable Long id){
        Product product = productRepo.findById(id).
                orElseThrow(()->new ProductNotFoundException(id));
        return productEntityAssembler.toModel(product);
    }
    /**
     * Returns all products as a collection of entity models.
     *
     * @return a collection of entity models representing all products
     */
    @GetMapping("/products")
    CollectionModel<EntityModel<Product>> getAllProducts(){
        List<EntityModel<Product>> products = productRepo.findAll().
                stream().map(product -> productEntityAssembler.toModel(product)).collect(Collectors.toList());
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class)
                .getAllProducts()).withSelfRel());
    }
    /**
     * Returns all products as a collection of DTOs.
     *
     * @return a response entity containing a collection of DTOs representing all products
     */
    @GetMapping("/products/info")
    public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> allProductsInfo(){
        return ResponseEntity.ok(productDtoAssembler.toCollectionModel(
                StreamSupport.stream(productRepo.findAll().spliterator(),false)
                        .map(ProductDTO::new).collect(Collectors.toList())));
    }


    /**
     * Returns the product identified by the given id as a DTO.
     *
     * @param id the id of the product to find
     * @return a response entity containing a DTO of the product identified by the id
     */

    @GetMapping("/products/{id}/info")
    public ResponseEntity<EntityModel<ProductDTO>> singleProductInfo(@PathVariable long id){
        return productRepo.findById(id)
                .map(ProductDTO::new)
                .map(productDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Returns all products with prices between the specified minimum and maximum.
     *
     * @param min the minimum price
     * @param max the maximum price
     * @return a collection of entity models representing products with prices between min and max
     */
    @GetMapping("products/betweenprice")
    CollectionModel<EntityModel<Product>> getProductsBetweenPrice(@RequestParam  double min,double max){
        List<EntityModel<Product>> products =  productRepo.findAll().stream().filter(product -> product.getPrice()>=min
                && product.getPrice()<=max)
                .map(productEntityAssembler::toModel).collect(Collectors.toList());
        System.out.println(max);
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());

    }
    /**
     * Returns all products with the given name.
     *
     * @param name the name of the product to find
     * @return a response entity containing a collection of entity models representing products with the given name
     */
    @GetMapping("/products/byname/{name}")
    ResponseEntity<?> getProductByName(@PathVariable String name){
        List<EntityModel<Product>> products = productRepo.getProductsByTitle(name)
                .stream().map(productEntityAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok().body(CollectionModel.of(products));
    }

    /**
     * Returns all products with ratings between the specified minimum and maximum.
     *
     * @param min the minimum rating
     * @param max the maximum rating
     * @return a collection of entity models representing products with ratings between min and max
     */

    @GetMapping("products/betweenrating")
    CollectionModel<EntityModel<Product>> getProductsBetweenRating(@RequestParam  double min,double max){
        List<EntityModel<Product>> products =  productRepo.findAll().stream().filter(product -> product.getRating()>=min
                        && product.getRating()<=max)
                .map(productEntityAssembler::toModel).collect(Collectors.toList());
        System.out.println(max);
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());

    }

    /**
     * Returns all products in the given category.
     *
     * @param category the category of products to find
     * @return a collection of entity models representing products in the given category
     */
    @GetMapping("products/category")
    CollectionModel<EntityModel<Product>> getProductsByCategory(@RequestParam(required = true)  String category){
        List<EntityModel<Product>> products =  productRepo.getProductsByCategory(category).stream()
                .map(productEntityAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());

    }


     //POST
    /**
     * Creates a new product.
     *
     * @param newProduct the product to create
     * @return a response entity containing an entity model of the created product
     */
    @PostMapping("/products")
    ResponseEntity<EntityModel<Product>> createProduct(@RequestBody Product newProduct){
        Product savedProduct = productRepo.save(newProduct);
        return ResponseEntity.created(linkTo(methodOn(ProductController.class).
                getProductById(savedProduct.getId())).toUri()).body(productEntityAssembler.toModel(savedProduct));
    }



    //PUT
    /**
     * Updates the title of the product identified by the given id.
     *
     * @param id the id of the product to update
     * @param updateTitle the new title
     * @return a response entity containing an entity model of the updated product
     */
    @PutMapping("/products/{id}")
    ResponseEntity<EntityModel<Product>> updateTitle(@PathVariable Long id, @RequestBody(required = true)  String updateTitle){
        EntityModel<Product> product = productRepo.findById(id).map(productToUpdate->{
            productToUpdate.setTitle(updateTitle.strip());
            return productRepo.save(productToUpdate);
        }).map(productEntityAssembler::toModel).orElseThrow(()->new ProductNotFoundException(id));
        return ResponseEntity.accepted().body(product);
    }

    /**
     * Updates the price of the product identified by the given id.
     *
     * @param id the id of the product to update
     * @param updatePrice the new price
     * @return a response entity containing an entity model of the updated product
     */
    @PutMapping("/products/editPrice/{id}")
    ResponseEntity<EntityModel<Product>> updatePrice(@PathVariable Long id, @RequestBody(required = true)  double updatePrice){
        EntityModel<Product> product = productRepo.findById(id).map(productToUpdate->{
            double newPrice=updatePrice;
            productToUpdate.setPrice(newPrice);
            return productRepo.save(productToUpdate);
        }).map(productEntityAssembler::toModel).orElseThrow(()->new ProductNotFoundException(id));
        return ResponseEntity.accepted().body(product);
    }


    //DELETE
    /**
     * Deletes the product identified by the given id.
     *
     * @param id the id of the product to delete
     * @return a response entity confirming the deletion of the product
     */
    @DeleteMapping("products/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productRepo.delete(productRepo.findById(id).orElseThrow(()->new ProductNotFoundException(id)));
        return ResponseEntity.ok("Deleted product id: " + id+ " success");
    }

}
