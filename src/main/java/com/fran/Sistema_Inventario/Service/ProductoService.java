package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<ProductoBasicoDTO> obtenerProductos();

    ProductoDetalladoDTO detallesProducto(Long id);

    Optional<Producto> obtenerPorID(Long id) throws IOException;

    Producto guardarProducto(Producto productoReq, MultipartFile file) throws IOException;

    void actualizarProducto(Producto productoReq);

    void actualizarImagenProducto(MultipartFile file, Long productoId) throws IOException;

    void eliminarProducto(Long id) throws IOException;

    MovimientoStock actualizarStock(MovimientoStock movimiento);

    void enviarAlertaStock();
}
