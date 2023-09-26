package com.demo.fakestoreproductservice2.services.proxy;

import com.demo.fakestoreproductservice2.config.Config;
import com.demo.fakestoreproductservice2.dtos.ProductDto;
import com.demo.fakestoreproductservice2.models.Category;
import com.demo.fakestoreproductservice2.models.Product;
import com.demo.fakestoreproductservice2.models.Rating;
import com.demo.fakestoreproductservice2.services.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceProxyImp implements ProductService {
    // Fields
    private RestTemplate restTemplate;
    private RestTemplate restTemplateHttpClient;
    private Logger log;

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<ProductDto[]> responseEntity = restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS, ProductDto[].class);
        if (responseEntity.hasBody()) {
            return Arrays.stream(responseEntity.getBody()).map(productDto -> this.mapToProduct(productDto))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Optional<Product> getSingleProduct(Long id) {
        ResponseEntity<ProductDto> responseEntity = restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS_RESOURCE, ProductDto.class, id);
        log.info(":: SERVICE :: getSingleProduct() :: isNull: " + (responseEntity.getBody()));
        return Optional.ofNullable(this.mapToProduct(responseEntity.getBody()));
    }

    @Override
    public Product addProduct(ProductDto newProduct) {
        ResponseEntity<ProductDto> responseEntity = restTemplate.postForEntity(Config.FAKE_STORE_PRODUCTS, newProduct, ProductDto.class);
        if (responseEntity.hasBody()) {
            return this.mapToProduct(responseEntity.getBody());
        }
        return null;
    }

    @Override
    public Product replaceProduct(Long id, ProductDto newProduct) {
        ResponseEntity<ProductDto> responseEntity = restTemplate.exchange(
                Config.FAKE_STORE_PRODUCTS_RESOURCE,
                HttpMethod.PUT,
                new HttpEntity<ProductDto>(newProduct),
                ProductDto.class,
                id);
        if (responseEntity.hasBody())
            return this.mapToProduct(responseEntity.getBody());
        return null;
    }

    // Way 1: Using patchForObject
    @Override
    public Optional<Product> updateProduct(Long id, ProductDto updatedProduct) {
        ProductDto productDto = restTemplateHttpClient.patchForObject(
                Config.FAKE_STORE_PRODUCTS_RESOURCE,
                updatedProduct,
                ProductDto.class,
                id
        );
        return Optional.ofNullable(this.mapToProduct(productDto));
    }

    @Override
    public Optional<Product> removeProduct(Long id) {
        ResponseEntity<ProductDto> responseEntity = restTemplate.exchange(
                Config.FAKE_STORE_PRODUCTS_RESOURCE,
                HttpMethod.DELETE,
                null,
                ProductDto.class,
                id
        );
        return Optional.ofNullable(this.mapToProduct(responseEntity.getBody()));
    }

    // WAY 2: Using exchange for PATCH
    /*
    @Override
    public Product updateProduct(Long id, ProductDto updatedProduct) {
        HttpHeaders reqHttpHeaders = new HttpHeaders();
        reqHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ProductDto> responseEntity = restTemplateHttpClient.exchange(
                Config.FAKE_STORE_PRODUCTS + "/{id}",
                HttpMethod.PATCH,
                new HttpEntity<ProductDto>(updatedProduct, reqHttpHeaders),
                ProductDto.class,
                id
        );
        if (responseEntity != null && responseEntity.hasBody()) {
            return this.mapToProduct(responseEntity.getBody());
        }
        return null;
    }
     */

    private Product mapToProduct(ProductDto productDto) {
        if (productDto == null) return null;
        Product product = Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .category(new Category(productDto.getCategory()))
                .image(productDto.getImage())
                .build();
        product.setId(productDto.getId());
        if (productDto.getRating() != null)
            product.setRating(new Rating(productDto.getRating().getRate(), productDto.getRating().getCount()));
        return product;
    }
}
