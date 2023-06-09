package com.devops.webapp;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Order {
    private @Id
    @GeneratedValue Long id;
    private Double totalPrice;
    private Integer numOfItems;
    @JsonIgnore @ManyToMany
    private List<Product> products = new ArrayList<>();

    @JsonIgnore @ManyToOne
    private Customer customer;

    public Order(List<Product> products, Customer customer) {
        this.products = products;
        this.customer = customer;
    }
    public Integer getNumOfItems(){
        return this.products.size();
    }

    public void addProduct(Product product){
        products.add(product);
        this.numOfItems = getNumOfItems();
        this.totalPrice = getTotalPrice();
    }
}
