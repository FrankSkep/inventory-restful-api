package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import java.util.List;

public interface ProductoService {

    public List<Producto> obtenerProductos();

    public Producto obtenerPorID(Integer id);

    public Producto guardarProducto(ProductoDTO producto);

    public Producto editarProducto(Integer id, ProductoDTO producto);

    public boolean eliminarProducto(Integer id);

    public MovimientoStock registrarMovimiento(MovimientoStock movimiento);
}
