package com.fran.inventory_api.system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponseSupplier {
    Long id;
    String name;
    String description;
    String categoryName;
}
