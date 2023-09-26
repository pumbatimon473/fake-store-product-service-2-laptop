package com.demo.fakestoreproductservice2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Rating extends BaseModel {
    // Fields
    private Double rate;
    private Integer count;
    @OneToOne
    private Product product;

    // CTOR
    public Rating(Double rate, Integer count) {
        this.rate = rate;
        this.count = count;
    }
}
