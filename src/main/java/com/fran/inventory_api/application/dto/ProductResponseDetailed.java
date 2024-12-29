package com.fran.inventory_api.application.dto;

import com.fran.inventory_api.application.entity.Movement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
