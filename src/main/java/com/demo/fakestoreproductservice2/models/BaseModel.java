package com.demo.fakestoreproductservice2.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseModel {
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
