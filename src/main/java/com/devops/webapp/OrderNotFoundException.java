package com.devops.webapp;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("order with id: " + id + " not found");
    }
}