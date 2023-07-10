package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Order entity. This class is used when you want to send data over network or
 * save to database in a specific format. It helps to ensure data encapsulation,
 * and can be helpful for performance optimization.
 */
@Value
@JsonPropertyOrder({"id","totalPrice","numOfItems","products"})
public class OrderDTO {

    @JsonIgnore
    private final Order order;

    /**
     * Fetches the id of the order.
     *
     * @return the id of the order
     */
    public Long getId() {
        return this.order.getId();
    }

    /**
     * Fetches the products of the order.
     *
     * @return the list of products of the order
     */
    public List<Product> getProducts() {
        return this.order.getProducts();
    }

    /**
     * Fetches the total price of the order.
     *
     * @return the total price of the order
     */
    public Double getTotalPrice() {
        return this.order.getTotalPrice();
    }

    /**
     * Fetches the number of items in the order.
     *
     * @return the number of items in the order
     */
    public int getNumOfItems() {
        return this.order.getNumOfItems();
    }
}
