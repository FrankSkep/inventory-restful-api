package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
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

    @Override
    public Producto obtenerPorID(Integer id) {
        return productoRepository.getReferenceById(id);
    }

    @Override
    public Producto guardarProducto(ProductoDTO productoReq) {

        Proveedor proveedor = proveedorService.obtenerPorID(productoReq.getProveedorId());

        Producto producto = new Producto();
        producto.setNombre(productoReq.getNombre());
        producto.setDescripcion(productoReq.getDescripcion());
        producto.setPrecio(productoReq.getPrecio());
        producto.setCantidadStock(productoReq.getCantidadStock());
        producto.setCategoria(productoReq.getCategoria());
        producto.setProveedor(proveedor);

        return productoRepository.save(producto);
    }

    @Override
    public Producto editarProducto(Integer id, ProductoDTO producto) {

        Producto productoDB = productoRepository.getReferenceById(id);

        productoDB.setNombre(producto.getNombre());
        productoDB.setDescripcion(producto.getDescripcion());
        productoDB.setPrecio(producto.getPrecio());
        productoDB.setCantidadStock(id);
        productoDB.setCategoria(producto.getCategoria());
        productoDB.setProveedor(proveedorService.obtenerPorID(producto.getProveedorId()));

        return productoRepository.save(productoDB);
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
