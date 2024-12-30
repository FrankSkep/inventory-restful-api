package com.fran.inventory_api.system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {

    private Long id;

    @NotBlank(message = "the name is mandatory")
    private String name;

    @NotBlank(message = "the description is mandatory")
    private String description;

    @NotNull(message = "the price is mandatory")
    @Min(value = 0, message = "the price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "the stock quantity is mandatory")
    @Min(value = 0, message = "the stock quantity must be greater than or equal to 0")
    private Long stock;

    @NotBlank(message = "the category is mandatory")
    private String category;

    private String imageUrl;

    @NotNull(message = "the supplier id is mandatory")
    private Long supplierId;

    @NotNull(message = "minimum stock alert is mandatory")
    @Min(value = 0, message = "the minimum stock alert must be greater than or equal to 0")
    private Integer minStock;
}
