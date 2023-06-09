package com.devops.webapp;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super("product with id: " + id + " not found");
    }
}