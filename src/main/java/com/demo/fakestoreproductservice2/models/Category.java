package com.demo.fakestoreproductservice2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Category extends BaseModel {
    // Fields
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // CTOR
    public Category(String name) {
        this.name = name;
    }
}
