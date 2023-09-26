package com.demo.fakestoreproductservice2.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    // Fields
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private RatingDto rating;

    // Behaviors
    public void setRating(RatingDto ratingDto) {
        this.rating = ratingDto;
    }
}
