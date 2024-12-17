package com.fran.inventory_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseSupplier {
    Long id;
    String name;
    String description;
    String categoryName;
}
