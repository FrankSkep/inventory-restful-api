package com.fran.inventory_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierResponseBasic {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String taxIdentification;
}
