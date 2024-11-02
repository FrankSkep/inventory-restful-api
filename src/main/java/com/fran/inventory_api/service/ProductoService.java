package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.Producto.ProductoResponseBasic;
import com.fran.inventory_api.dto.Producto.ProductoResponseDetailed;
import com.fran.inventory_api.entity.MovimientoStock;
import com.fran.inventory_api.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductoService {

    Page<ProductoResponseBasic> getProductsPage(Pageable pageable);

    List<ProductoResponseBasic> getAllProducts();

    ProductoResponseDetailed getProductDetails(Long id);

    Producto save(Producto productoReq, MultipartFile file);

    void update(Producto productoReq);

    void updateImage(MultipartFile file, Long productoId);

    void delete(Long id) throws IOException;

    MovimientoStock updateStock(MovimientoStock movimiento);

    void sendNotification(String message);
}
