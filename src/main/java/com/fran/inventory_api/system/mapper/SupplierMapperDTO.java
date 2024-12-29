package com.fran.inventory_api.system.mapper;

import com.fran.inventory_api.system.dto.SupplierResponseBasic;
import com.fran.inventory_api.system.dto.SupplierRequest;
import com.fran.inventory_api.system.entity.Supplier;

import org.springframework.stereotype.Component;

@Component
public class SupplierMapperDTO {

    public SupplierResponseBasic toDTObasic(Supplier supplier) {
        return new SupplierResponseBasic(
                supplier.getId(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getTaxIdentification()
        );
    }

    // Mapeo para agregacion de nuevo proveedor
    public Supplier toEntity(SupplierRequest dto) {
        return new Supplier(
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getTaxIdentification()
        );
    }

    // Mapeo para edicion de proveedor
    public Supplier toEntityFromDTOWithId(SupplierRequest dto) {
        return new Supplier(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getTaxIdentification()
        );
    }
}
