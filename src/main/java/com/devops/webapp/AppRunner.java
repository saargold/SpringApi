package com.devops.webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class AppRunner  implements CommandLineRunner {

    private final ProductStoreService productStoreService;
    private final ProductRepo productRepo;

    public AppRunner(@Lazy ProductStoreService productStoreService, ProductRepo productRepo){
        this.productStoreService = productStoreService;
        this.productRepo = productRepo;
    }
    @Override
    public void run(String... args) throws Exception {
        CompletableFuture<ProductStore[]> futureProducts = productStoreService.getProducts();
        CompletableFuture.allOf(futureProducts).join();

        ProductStore[] products = futureProducts.get();
        for(ProductStore p: products){
            productRepo.save(new Product(p.getTitle(),p.getPrice(),p.getCategory(),p.getImage()));
        }

    }
}
