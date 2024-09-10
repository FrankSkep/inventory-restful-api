package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {

    public List<ProductoBasicoDTO> obtenerProductos();

    public ProductoDetalladoDTO detallesProducto(Long id);

    public Producto guardarProducto(ProductoDTO producto);

    public void actualizarProducto(ProductoDTO productoReq);

    public void updateFile(Long productoId, MultipartFile file);

    public boolean eliminarProducto(Long id);

    public MovimientoStock actualizarStock(MovimientoStock movimiento);

    public void enviarAlertaStock();
}
