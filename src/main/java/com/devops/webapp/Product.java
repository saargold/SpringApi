package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Product  implements Comparable<Product>{
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public int compareTo(Product o) {
        return Double.compare(this.getPrice(), o.getPrice());
    }

    private String title;
    private double price;

    private String category;

    private String image;
    @JsonIgnore

//    @JsonIgnore @ManyToMany(mappedBy = "products")
//    private List<StoreOrder> storeOrders = new ArrayList<>();


    public Product(String title, double price, String category, String image) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.image = image;
    }
}