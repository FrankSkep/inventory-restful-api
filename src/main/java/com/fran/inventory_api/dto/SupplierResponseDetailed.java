package com.fran.inventory_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SupplierResponseDetailed {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String taxIdentification;
    private Set<ProductResponseSupplier> products;
}
