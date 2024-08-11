package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    private ProductoRepository productoRepository;
    private MovimientoStockRepository movimientoStockRepository;
    private ProveedorService proveedorService;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, MovimientoStockRepository movimientoStockRepository, ProveedorService proveedorService) {
        this.productoRepository = productoRepository;
        this.movimientoStockRepository = movimientoStockRepository;
        this.proveedorService = proveedorService;
    }

    private final String BUCKET_NAME = "productos-inventario-7f2d7.appspot.com";

    @Override
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por su ID
    @Override
    public Producto obtenerPorID(Long id) {
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
                productoReq.getImageUrl(),
                proveedorService.obtenerPorID(productoReq.getProveedorId()));

        return productoRepository.save(producto);
    }

    // Editar datos de un producto existente
    @Override
    public Producto editarProducto(Long id, ProductoDTO productoReq) {

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
    public boolean eliminarProducto(Long id) {

        Producto producto = productoRepository.getReferenceById(id);

        if (producto != null) {
            // Obtener la URL de la imagen asociada al producto
            String imageUrl = producto.getImageUrl();

            // Extraer el nombre del archivo desde la URL
            String fileName = extractFileNameFromUrl(imageUrl);

            // Eliminar la imagen de Firebase Storage
            deleteImageFromFirebase(fileName);

            // Eliminar el producto de la base de datos
            productoRepository.delete(producto);
            return true;
        } else {
            return false;
        }
    }

    private String extractFileNameFromUrl(String imageUrl) {
        // Extrae el nombre del archivo de la URL
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    private void deleteImageFromFirebase(String fileName) {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Referencia al archivo en Firebase Storage
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);

        // Eliminar el archivo
        boolean deleted = storage.delete(blobId);
        if (!deleted) {
            throw new RuntimeException("No se pudo eliminar la imagen de Firebase Storage");
        }
    }

    @Override
    @Transactional
    public MovimientoStock registrarMovimiento(MovimientoStock movimiento) {

        Producto producto = productoRepository.getReferenceById(movimiento.getProducto().getId());

        if (producto == null) {
            throw new EntityNotFoundException("Producto no encontrado");
        }

        Long stockActual = producto.getCantidadStock();
        Long stockMovimiento = movimiento.getCantidad();

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
