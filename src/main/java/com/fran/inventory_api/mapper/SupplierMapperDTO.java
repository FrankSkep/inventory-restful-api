package com.fran.inventory_api.mapper;

import com.fran.inventory_api.dto.ProductResponseProv;
import com.fran.inventory_api.dto.SupplierResponseBasic;
import com.fran.inventory_api.dto.SupplierRequest;
import com.fran.inventory_api.dto.SupplierResponseDetailed;
import com.fran.inventory_api.entity.Product;
import com.fran.inventory_api.entity.Supplier;

import java.util.Set;
import java.util.stream.Collectors;

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

    public SupplierRequest toDTO(Supplier supplier) {
        return new SupplierRequest(
                supplier.getId(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getTaxIdentification()
        );
    }

    public SupplierResponseDetailed toDTOdetailed(Supplier supplier) {

        Set<Product> products = supplier.getProducts();
        Set<ProductResponseProv> setDeProductos = products.stream().map(this::productoToDTOminimo).collect(Collectors.toSet());

        return new SupplierResponseDetailed(
                supplier.getId(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getTaxIdentification(),
                setDeProductos
        );
    }

    public ProductResponseProv productoToDTOminimo(Product product) {
        return new ProductResponseProv(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getName(),
                product.getImage() != null ? product.getImage().getUrl() : null
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
