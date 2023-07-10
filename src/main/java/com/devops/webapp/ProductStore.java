package com.devops.webapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductStore {
    private Long id;
    private String title;
    private double price;

    private String category;

    private String image;
    private Rating rating;


    @Override
    public String toString() {
        return "ProductStore{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", price=" + this.getPrice() +
                ", category='" + this.getCategory() + '\'' +
                ", image='" + this.getImage() + '\'' +
                ", rating='" + this.getRating().getRate() + '\''+

                '}';
    }
}
