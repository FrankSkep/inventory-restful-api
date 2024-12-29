package com.fran.inventory_api.system.service;

import com.fran.inventory_api.system.dto.ProductResponseBasic;
import com.fran.inventory_api.system.entity.Movement;
import com.fran.inventory_api.system.entity.Product;
import com.fran.inventory_api.system.dto.ProductResponseDetailed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Page<ProductResponseBasic> getProductsPage(Pageable pageable);

    List<ProductResponseBasic> getAllProducts();

    ProductResponseDetailed getProductDetails(Long id);

    Product createProduct(Product productReq, MultipartFile file);

    void updateProduct(Product productReq);

    void updateProductImage(MultipartFile file, Long productoId);

    void deleteProduct(Long id);

    Movement updateStock(Movement movement);
}
