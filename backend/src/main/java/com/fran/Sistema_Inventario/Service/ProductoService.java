package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import java.util.List;

public interface ProductoService {

    public List<Producto> obtenerProductos();

    public Producto obtenerPorID(Long id);

    public Producto guardarProducto(ProductoDTO producto);

    public Producto editarProducto(Long id, ProductoDTO producto);

    public boolean eliminarProducto(Long id);

    public MovimientoStock actualizarStock(MovimientoStock movimiento);
}
