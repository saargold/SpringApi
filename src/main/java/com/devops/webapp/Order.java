package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalPrice;
    private Integer numOfItems;

    @JsonIgnore @ManyToMany
    private List<Product> products = new ArrayList<>();
    @JsonIgnore @ManyToOne
    private User user;

    /**
     * Constructor to create an Order with a list of products and a User.
     * It also calculates the total price and number of items.
     */
    public Order(List<Product> products, User user) {
        this.products = products;
        this.user = user;
        this.totalPrice = getTotalPrice();
        this.numOfItems = getNumOfItems();
    }

    /**
     * Calculates the total price of the Order.
     *
     * @return the total price of the Order
     */
    public Double getTotalPrice() {
        Double sum = 0d;
        for(Product p: this.products){
            sum+=p.getPrice();
        }
        return sum;
    }

    /**
     * Fetches the products of the Order.
     *
     * @return the list of products of the Order
     */
    public List<Product> getProducts() {
        return this.products;
    }

    /**
     * Fetches the number of items in the Order.
     *
     * @return the number of items in the Order
     */
    public Integer getNumOfItems() {
        return this.products.size();
    }

    /**
     * Adds a new Product to the Order and recalculates the total price and number of items.
     *
     * @param product the product to add to the Order
     */
    public void addProduct(Product product) {
        products.add(product);
        this.numOfItems = getNumOfItems();
        this.totalPrice = getTotalPrice();
    }
}