package com.demo.fakestoreproductservice2.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RatingDto {
    // Fields
    private Double rate;
    private Integer count;
}
