package com.fran.inventory_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseProv {

    Long id;
    String name;
    String description;
    Double price;
    String categoryName;
    String imageUrl;

}
