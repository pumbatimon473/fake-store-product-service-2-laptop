package com.demo.fakestoreproductservice2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Builder
public class Product extends BaseModel {
    // Fields
    private String title;
    private Double price;
    private String description;
    @ManyToOne
    private Category category;
    private String image;
    @OneToOne(mappedBy = "product")
    private Rating rating;

    // CTOR
    public Product(String title, Double price, String description, Category category, String image, Rating rating) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rating = rating;
    }

    // Behavior
    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
