package com.devops.webapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductStoreService {
    private RestTemplate restTemplate;

    @Autowired
    public ProductStoreService(RestTemplateBuilder builder){
        this.restTemplate=builder.build();
    }

    @Async
    public CompletableFuture<ProductStore[]> getProducts(){
        String url = String.format("https://fakestoreapi.com/products");
        ProductStore[] products =  restTemplate.getForEntity(url, ProductStore[].class).getBody();
        return CompletableFuture.completedFuture(products);
    }


}
