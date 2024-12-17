package com.fran.inventory_api.mapper;

import com.fran.inventory_api.dto.ProductResponseBasic;
import com.fran.inventory_api.dto.ProductRequest;
import com.fran.inventory_api.dto.ProductResponseDetailed;
import com.fran.inventory_api.entity.Product;
import com.fran.inventory_api.service.CategoryService;
import com.fran.inventory_api.service.SupplierService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperDTO {

    private final SupplierMapperDTO proveedorMapper;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    public ProductMapperDTO(SupplierMapperDTO proveedorMapper, CategoryService categoryService, SupplierService supplierService) {
        this.proveedorMapper = proveedorMapper;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    public ProductRequest toDTO(Product product) {
        return new ProductRequest(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName(),
                product.getImage() != null ? product.getImage().getUrl() : null,
                product.getSupplier().getId(),
                product.getMinStock());
    }

    public ProductResponseBasic toDTObasic(Product product) {
        return new ProductResponseBasic(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName(),
                product.getImage() != null ? product.getImage().getUrl() : null
        );
    }

    public ProductResponseDetailed toDTOdetailed(Product product) {
        return new ProductResponseDetailed(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName(),
                product.getImage() != null ? product.getImage().getUrl() : null,
                proveedorMapper.toDTObasic(product.getSupplier()),
                product.getMinStock(),
                product.getStockMovements()
        );
    }

    public Product toEntity(ProductRequest product) {
        return new Product(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                categoryService.getByName(product.getCategory()),
                supplierService.getById(product.getSupplierId()),
                product.getMinStock()
        );
    }

    public Product toEntityWithId(ProductRequest product) {
        return new Product(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                categoryService.getByName(product.getCategory()),
                supplierService.getById(product.getSupplierId()),
                product.getMinStock()
        );
    }
}
