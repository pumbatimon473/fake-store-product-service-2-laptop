package com.demo.fakestoreproductservice2.services;

import com.demo.fakestoreproductservice2.dtos.ProductDto;
import com.demo.fakestoreproductservice2.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getSingleProduct(Long id);

    Product addProduct(ProductDto newProduct);

    Product replaceProduct(Long id, ProductDto newProduct);

    Optional<Product> updateProduct(Long id, ProductDto updatedProduct);

    Optional<Product> removeProduct(Long id);
}
