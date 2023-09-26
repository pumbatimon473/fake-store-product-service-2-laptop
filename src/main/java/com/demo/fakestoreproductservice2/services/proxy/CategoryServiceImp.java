package com.demo.fakestoreproductservice2.services.proxy;

import com.demo.fakestoreproductservice2.config.Config;
import com.demo.fakestoreproductservice2.dtos.ProductDto;
import com.demo.fakestoreproductservice2.models.Category;
import com.demo.fakestoreproductservice2.models.Product;
import com.demo.fakestoreproductservice2.models.Rating;
import com.demo.fakestoreproductservice2.services.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService {
    // Fields
    private RestTemplate restTemplate;
    private Logger log;

    @Override
    public List<Category> getAllCategory() {
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS_CATEGORIES, String[].class);
        if (responseEntity.hasBody()) {
            return Arrays.stream(responseEntity.getBody())
                    .map(categoryName -> new Category(categoryName))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        ResponseEntity<ProductDto[]> responseEntity = restTemplate.getForEntity(
                Config.FAKE_STORE_PRODUCTS_BY_CATEGORY,
                ProductDto[].class,
                categoryName
        );
        if (responseEntity.hasBody()) {
            return Arrays.stream(responseEntity.getBody())
                    .map(productDto -> this.mapToProduct(productDto))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private Product mapToProduct(ProductDto productDto) {
        Product product = Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .category(new Category(productDto.getCategory()))
                .image(productDto.getImage())
                .rating(new Rating(productDto.getRating().getRate(), productDto.getRating().getCount()))
                .build();
        product.setId(productDto.getId());
        return product;
    }
}
