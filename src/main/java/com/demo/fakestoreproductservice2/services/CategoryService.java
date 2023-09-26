package com.demo.fakestoreproductservice2.services;

import com.demo.fakestoreproductservice2.models.Category;
import com.demo.fakestoreproductservice2.models.Product;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();

    List<Product> getProductsByCategory(String categoryName);
}
