package com.fran.inventory_api.system.dto;

import com.fran.inventory_api.system.entity.Movement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
