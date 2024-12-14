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
                supplier.getNombre(),
                supplier.getDireccion(),
                supplier.getEmail(),
                supplier.getTelefono(),
                supplier.getIdentificacionFiscal()
        );
    }

    public SupplierRequest toDTO(Supplier supplier) {
        return new SupplierRequest(
                supplier.getId(),
                supplier.getNombre(),
                supplier.getDireccion(),
                supplier.getEmail(),
                supplier.getTelefono(),
                supplier.getIdentificacionFiscal()
        );
    }

    public SupplierResponseDetailed toDTOdetailed(Supplier supplier) {

        Set<Product> products = supplier.getProducts();
        Set<ProductResponseProv> setDeProductos = products.stream().map(this::productoToDTOminimo).collect(Collectors.toSet());

        return new SupplierResponseDetailed(
                supplier.getId(),
                supplier.getNombre(),
                supplier.getDireccion(),
                supplier.getEmail(),
                supplier.getTelefono(),
                supplier.getIdentificacionFiscal(),
                setDeProductos
        );
    }

    public ProductResponseProv productoToDTOminimo(Product product) {
        return new ProductResponseProv(
                product.getId(),
                product.getNombre(),
                product.getDescripcion(),
                product.getPrecio(),
                product.getCategory().getNombre(),
                product.getImage() != null ? product.getImage().getUrl() : null
        );
    }

    // Mapeo para agregacion de nuevo proveedor
    public Supplier toEntity(SupplierRequest dto) {
        return new Supplier(
                dto.getNombre(),
                dto.getDireccion(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getIdentificacionFiscal()
        );
    }

    // Mapeo para edicion de proveedor
    public Supplier toEntityFromDTOWithId(SupplierRequest dto) {
        return new Supplier(
                dto.getId(),
                dto.getNombre(),
                dto.getDireccion(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getIdentificacionFiscal()
        );
    }


}
