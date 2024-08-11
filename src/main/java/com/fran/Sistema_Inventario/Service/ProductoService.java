package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import java.util.List;

public interface ProductoService {

    public List<ProductoBasicoDTO> obtenerProductos();

    public ProductoDetalladoDTO obtenerPorID(Long id);

    public Producto guardarProducto(ProductoDTO producto);

    public Producto editarProducto(Long id, ProductoDTO producto);

    public boolean eliminarProducto(Long id);

    public MovimientoStock actualizarStock(MovimientoStock movimiento);
}
