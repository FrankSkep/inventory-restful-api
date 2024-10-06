package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductoService {

    Page<ProductoBasicoDTO> getProductsPage(Pageable pageable);

    List<ProductoBasicoDTO> getAllProducts();

    ProductoDetalladoDTO productDetails(Long id);

    Producto saveProduct(Producto productoReq, MultipartFile file);

    void updateProduct(Producto productoReq);

    void updateProductImage(MultipartFile file, Long productoId);

    void deleteProduct(Long id) throws IOException;

    MovimientoStock updateStock(MovimientoStock movimiento);

    void sendEmail();
}
