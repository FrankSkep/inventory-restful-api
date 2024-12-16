package com.fran.inventory_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponseBasic {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long stock;
    private String category;
    private String imageUrl;
}
