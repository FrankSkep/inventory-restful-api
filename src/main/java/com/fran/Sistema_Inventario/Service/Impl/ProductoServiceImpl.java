package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

    // Bucket de firebase
    private final String BUCKET_NAME = "productos-inventario-7f2d7.appspot.com";

    @Override
    public List<ProductoBasicoDTO> obtenerProductos() {

        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(this::convertirABasicoDTO).collect(Collectors.toList());
    }

    private ProductoBasicoDTO convertirABasicoDTO(Producto producto) {

        ProductoBasicoDTO dto = new ProductoBasicoDTO(
                producto.getId(), producto.getNombre(),
                producto.getDescripcion(), producto.getPrecio(),
                producto.getCantidadStock(), producto.getCategoria(),
                producto.getImageUrl()
        );
        return dto;
    }

    // Obtener producto por su ID
    @Override
    public ProductoDetalladoDTO obtenerPorID(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        return new ProductoDetalladoDTO(
                producto.getId(), producto.getNombre(),
                producto.getDescripcion(), producto.getPrecio(),
                producto.getCantidadStock(), producto.getCategoria(),
                producto.getImageUrl(), ProveedorServiceImpl.convertirDTOrespuesta(producto.getProveedor()),
                producto.getUmbralBajoStock(), producto.getMovimientosStock()
        );
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
                proveedorService.obtenerPorID(productoReq.getProveedorId()),
                productoReq.getUmbralBajoStock());

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

            if (imageUrl != null && !imageUrl.isEmpty()) {
                // Extraer el nombre del archivo desde la URL
                String fileName = extractFileNameFromUrl(imageUrl);

                System.out.println("Filename: " + fileName);

                // Eliminar la imagen de Firebase Storage
                try {
                    deleteImageFromFirebase(fileName);
                } catch (Exception e) {
                    // Manejar el error de eliminación
                    throw new RuntimeException("Error al eliminar la imagen de Firebase Storage: " + e.getMessage());
                }
            }

            // Eliminar el producto de la base de datos
            productoRepository.delete(producto);
            return true;
        } else {
            return false;
        }
    }

    // Obtener nombre de imagen desde url
    private String extractFileNameFromUrl(String imageUrl) {
        // Extrae el nombre del archivo de la URL
        String fileName = imageUrl.substring(imageUrl.indexOf("/o/") + 3);
        // Elimina cualquier parámetro de consulta, si está presente
        int queryParamIndex = fileName.indexOf("?");
        if (queryParamIndex != -1) {
            fileName = fileName.substring(0, queryParamIndex);
        }
        // Reemplaza las codificaciones de URL
        fileName = fileName.replace("%2F", "/");
        return fileName;
    }

    // Eliminar imagen de firebase
    private void deleteImageFromFirebase(String fileName) {
        Storage storage = StorageClient.getInstance().bucket().getStorage();

        // Referencia al archivo en Firebase Storage
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        System.out.println("Blob id: " + blobId.toString());

        // Eliminar el archivo  
        boolean deleted = storage.delete(blobId);
        if (!deleted) {
            throw new RuntimeException("No se pudo eliminar la imagen de Firebase Storage");
        }
    }

    // Actualiza el stock y registra el movimiento
    @Override
    @Transactional
    public MovimientoStock actualizarStock(MovimientoStock movimiento) {

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

        if (producto.getCantidadStock() <= producto.getUmbralBajoStock()) {
            enviarAlertaStock();
        }

        movimiento.setProducto(producto);
        movimiento.setFechaMovimiento(LocalDateTime.now());

        productoRepository.save(producto);
        return movimientoStockRepository.save(movimiento);
    }

    public void enviarAlertaStock() {
        // Aqui implementare la notificacion por correo

    }
}
