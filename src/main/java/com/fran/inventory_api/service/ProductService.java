package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.ProductResponseBasic;
import com.fran.inventory_api.dto.ProductResponseDetailed;
import com.fran.inventory_api.entity.Movement;
import com.fran.inventory_api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<ProductResponseBasic> getProductsPage(Pageable pageable);

    List<ProductResponseBasic> getAllProducts();

    ProductResponseDetailed getProductDetails(Long id);

    Product save(Product productReq, MultipartFile file);

    void update(Product productReq);

    void updateImage(MultipartFile file, Long productoId);

    void delete(Long id) throws IOException;

    Movement updateStock(Movement movement);
}
