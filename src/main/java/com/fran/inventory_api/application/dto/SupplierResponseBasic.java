package com.fran.inventory_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SupplierResponseBasic {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String taxIdentification;
}
