package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.MapperDTO.ProductoMapperDTO;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoStockRepository movimientoStockRepository;
    private final ProveedorService proveedorService;
    private final ProductoMapperDTO productoMapper;
    private CloudinaryServiceImpl cloudinaryService;

    public ProductoServiceImpl(ProductoRepository productoRepository, MovimientoStockRepository movimientoStockRepository,
                               ProveedorService proveedorService, ProductoMapperDTO productoMapper, CloudinaryServiceImpl cloudinaryService) {
        this.productoRepository = productoRepository;
        this.movimientoStockRepository = movimientoStockRepository;
        this.proveedorService = proveedorService;
        this.productoMapper = productoMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<ProductoBasicoDTO> obtenerProductos() {

        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(productoMapper::toDTObasic).collect(Collectors.toList());
    }

    // Obtener producto por su ID
    @Override
    public ProductoDetalladoDTO detallesProducto(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        return productoMapper.toDTOdetailed(producto);
    }

    // Guardar un nuevo producto
    @Override
    public Producto guardarProducto(ProductoDTO productoReq) {

        Producto producto = productoMapper.toEntity(productoReq);

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
            String publicId = producto.getImageId();

            if (publicId != null && !publicId.isEmpty()) {
                try {
                    // Eliminar la imagen de Cloudinary usando el public_id
                    cloudinaryService.deleteFile(publicId);
                } catch (IOException e) {
                    throw new RuntimeException("Error al eliminar la imagen de Cloudinary: " + e.getMessage(), e);
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
