package com.demo.fakestoreproductservice2.controllers;

import com.demo.fakestoreproductservice2.config.Config;
import com.demo.fakestoreproductservice2.dtos.ProductDto;
import com.demo.fakestoreproductservice2.dtos.RatingDto;
import com.demo.fakestoreproductservice2.models.Product;
import com.demo.fakestoreproductservice2.services.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class CategoryController {
    // Fields
    private CategoryService categoryService;
    private Logger log;

    // Behaviors
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        ResponseEntity<List<String>> response;
        try {
            response = new ResponseEntity<>(
                    categoryService.getAllCategory().stream()
                            .map(category -> category.getName())
                            .collect(Collectors.toList()),
                    HttpStatus.OK
            );
        } catch (NullPointerException e) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            this.log("Exception caught in method 'getAllProducts()': " + e.getClass() + ": " + e.getMessage());
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable(name = "categoryName") String categoryName) {
        ResponseEntity<List<ProductDto>> response;
        try {
            response = new ResponseEntity<>(
                    categoryService.getProductsByCategory(categoryName).stream()
                            .map(product -> this.mapToProductDto(product))
                            .collect(Collectors.toList()),
                    HttpStatus.OK
            );
        } catch (NullPointerException e) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            this.log("Exception caught in method 'getAllProducts()': " + e.getClass() + ": " + e.getMessage());
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory().getName())
                .image(product.getImage())
                .rating(new RatingDto(product.getRating().getRate(), product.getRating().getCount()))
                .build();
    }

    private void log(String message) {
        if (Config.ENABLE_LOGGING)
            this.log.info(message);
    }
}
