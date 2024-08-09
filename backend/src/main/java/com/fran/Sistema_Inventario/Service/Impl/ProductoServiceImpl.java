package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoStockRepository movimientoStockRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Override
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por su ID
    @Override
    public Producto obtenerPorID(Integer id) {
        return productoRepository.getReferenceById(id);
    }

    // Guardar un nuevo producto
    @Override
    public Producto guardarProducto(ProductoDTO productoReq) {

        Producto producto = new Producto(
                productoReq.getNombre(),
                productoReq.getDescripcion(),
                productoReq.getPrecio(),
                productoReq.getCantidadStock(),
                productoReq.getCategoria(),
                proveedorService.obtenerPorID(productoReq.getProveedorId()));

        return productoRepository.save(producto);
    }

    // Editar datos de un producto existente
    @Override
    public Producto editarProducto(Integer id, ProductoDTO productoReq) {

        Producto productoDB = productoRepository.getReferenceById(id);

        productoDB.setNombre(productoReq.getNombre());
        productoDB.setDescripcion(productoReq.getDescripcion());
        productoDB.setPrecio(productoReq.getPrecio());
        productoDB.setCategoria(productoReq.getCategoria());
        productoDB.setProveedor(proveedorService.obtenerPorID(productoReq.getProveedorId()));

        return productoRepository.save(productoDB);
    }

    // Eliminar un producto
    @Override
    public boolean eliminarProducto(Integer id) {

        Producto producto = productoRepository.getReferenceById(id);

        if (producto != null) {
            productoRepository.delete(producto);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public MovimientoStock registrarMovimiento(MovimientoStock movimiento) {

        Producto producto = productoRepository.getReferenceById(movimiento.getProducto().getId());

        if (producto == null) {
            throw new EntityNotFoundException("Producto no encontrado");
        }

        Integer stockActual = producto.getCantidadStock();
        Integer stockMovimiento = movimiento.getCantidad();

        if (movimiento.getTipoMovimiento() == MovimientoStock.TipoMovimiento.SALIDA) {
            if (stockActual < stockMovimiento) {
                throw new IllegalArgumentException("No hay suficiente stock para realizar esta operación");
            }
            producto.setCantidadStock(stockActual - stockMovimiento);
        } else {
            producto.setCantidadStock(stockActual + stockMovimiento);
        }

        movimiento.setProducto(producto);
        movimiento.setFechaMovimiento(LocalDateTime.now());

        productoRepository.save(producto);
        return movimientoStockRepository.save(movimiento);
    }
}
