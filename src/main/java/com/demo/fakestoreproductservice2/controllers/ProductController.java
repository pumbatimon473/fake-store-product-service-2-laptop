package com.demo.fakestoreproductservice2.controllers;

import com.demo.fakestoreproductservice2.config.Config;
import com.demo.fakestoreproductservice2.dtos.ProductDto;
import com.demo.fakestoreproductservice2.dtos.RatingDto;
import com.demo.fakestoreproductservice2.models.Product;
import com.demo.fakestoreproductservice2.services.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    // Fields
    private ProductService productService;
    private Logger log;

    // Behaviors
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        ResponseEntity<List<ProductDto>> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(
                    productService.getAllProducts().stream()
                            .map(product -> this.mapToProductDto(product))
                            .collect(Collectors.toList()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            this.log("Exception caught in method 'getAllProducts()': " + e.getClass() + ": " + e.getMessage());
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable(name = "id") Long id) {
        ResponseEntity<ProductDto> responseEntity;
        try {
            responseEntity = productService.getSingleProduct(id)
                    .map(product -> new ResponseEntity<>(this.mapToProductDto(product), HttpStatus.OK))
                    .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            this.log(":: Exception caught in getSingleProduct(): " + e.getClass() + ": " + e.getMessage());
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto newProduct) {
        ResponseEntity<ProductDto> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(
                    this.mapToProductDto(productService.addProduct(newProduct)),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            this.log("Exception caught in method 'addProduct()': " + e.getClass() + ": " + e.getMessage());
            responseEntity = new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto newProduct) {
        ResponseEntity<ProductDto> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(
                    this.mapToProductDto(productService.replaceProduct(id, newProduct)),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            this.log("Exception caught in method 'replaceProduct()': " + e.getClass() + ": " + e.getMessage());
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto updatedProduct) {
        ResponseEntity<ProductDto> responseEntity;
        try {
            responseEntity = productService.updateProduct(id, updatedProduct)
                    .map(product -> new ResponseEntity<>(this.mapToProductDto(product), HttpStatus.OK))
                    .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            this.log("Exception caught in method 'updateProduct()': " + e.getClass() + ": " + e.getMessage());
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable(name = "id") Long id) {
        ResponseEntity<ProductDto> responseEntity;
        try {
            responseEntity = productService.removeProduct(id)
                    .map(product -> new ResponseEntity<>(this.mapToProductDto(product), HttpStatus.OK))
                    .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            this.log("Exception caught in method 'removeProduct()': " + e.getClass() + ": " + e.getMessage());
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    private ProductDto mapToProductDto(Product product) {
        if (product == null) return null;
        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory().getName())
                .image(product.getImage())
                .build();
        if (product.getRating() != null)
            productDto.setRating(new RatingDto(product.getRating().getRate(), product.getRating().getCount()));
        return productDto;
    }

    private void log(String message) {
        if (Config.ENABLE_LOGGING)
            this.log.info(message);
    }
}
