package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Product  implements Comparable<Product>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public int compareTo(Product o) {
        return Double.compare(this.getPrice(), o.getPrice());
    }

    private String title;
    private double price;

    private String category;

    private String image;
    private Double rating;


    @JsonIgnore @ManyToMany
    private List<Order> orders  =new ArrayList<>();;
    public Product(String title, double price, String category, String image, Double rating) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.image = image;
        this.rating=rating;
    }
}