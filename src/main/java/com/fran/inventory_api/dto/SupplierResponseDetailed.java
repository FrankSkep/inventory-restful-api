package com.fran.inventory_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SupplierResponseDetailed {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String taxIdentification;
    private Set<ProductResponseProv> products;
}
