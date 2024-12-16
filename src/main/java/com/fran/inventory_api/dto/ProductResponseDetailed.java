package com.fran.inventory_api.dto;

import com.fran.inventory_api.entity.Movement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDetailed {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long stock;
    private String category;
    private String imageUrl;
    private SupplierResponseBasic supplier;
    private Integer minStock;
    private List<Movement> stockMovements;
}
