package com.fran.inventory_api.system.mapper;

import com.fran.inventory_api.system.entity.Product;
import com.fran.inventory_api.system.dto.ProductRequest;
import com.fran.inventory_api.system.dto.ProductResponseDetailed;
import com.fran.inventory_api.system.service.CategoryService;
import com.fran.inventory_api.system.service.SupplierService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final SupplierMapper supplierMapper;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    public ProductMapper(SupplierMapper supplierMapper, CategoryService categoryService, SupplierService supplierService) {
        this.supplierMapper = supplierMapper;
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

    public ProductResponseDetailed toDTOdetailed(Product product) {
        return new ProductResponseDetailed(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName(),
                product.getImage() != null ? product.getImage().getUrl() : null,
                supplierMapper.toDTObasic(product.getSupplier()),
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
                categoryService.getCategoryByName(product.getCategory()),
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
                categoryService.getCategoryByName(product.getCategory()),
                supplierService.getById(product.getSupplierId()),
                product.getMinStock()
        );
    }
}
