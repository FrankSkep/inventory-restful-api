package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.Producto.ProductoResponseBasic;
import com.fran.Sistema_Inventario.DTO.Producto.ProductoResponseDetailed;
import com.fran.Sistema_Inventario.Entity.Imagen;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.MapperDTO.ProductoMapperDTO;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.ImagenService;
import com.fran.Sistema_Inventario.Service.NotificacionService;
import com.fran.Sistema_Inventario.Service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoStockRepository movimientoStockRepository;
    private final ProductoMapperDTO productoMapper;
    private final ImagenService imagenService;
    private final NotificacionService notificacionService;

    public ProductoServiceImpl(ProductoRepository productoRepository, MovimientoStockRepository movimientoStockRepository,
                               ProductoMapperDTO productoMapper, ImagenService imagenService,
                               NotificacionService notificacionService) {
        this.productoRepository = productoRepository;
        this.movimientoStockRepository = movimientoStockRepository;
        this.productoMapper = productoMapper;
        this.imagenService = imagenService;
        this.notificacionService = notificacionService;
    }

    // Obtener todos los productos y sus datos basicos
    @Override
    public List<ProductoResponseBasic> getAllProducts() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(productoMapper::toDTObasic).collect(Collectors.toList());
    }

    @Override
    public Page<ProductoResponseBasic> getProductsPage(Pageable pageable) {
        return productoRepository.findAll(pageable).map(productoMapper::toDTObasic);
    }

    // Obtener detalles de un producto
    @Override
    public ProductoResponseDetailed getProductDetails(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        return productoMapper.toDTOdetailed(producto);
    }

    // Guardar un nuevo producto
    @Transactional
    @Override
    public Producto save(Producto producto, MultipartFile file) {

        if (file != null) {
            Imagen imagen = imagenService.uploadImage(file);
            producto.setImagen(imagen);
        }
        return productoRepository.save(producto);
    }

    // Editar datos de un producto existente
    @Override
    public void update(Producto producto) {

        Producto productoDB = productoRepository.getReferenceById(producto.getId());

        productoDB.setNombre(producto.getNombre());
        productoDB.setDescripcion(producto.getDescripcion());
        productoDB.setPrecio(producto.getPrecio());
        productoDB.setCategoria(producto.getCategoria());
        productoDB.setProveedor(producto.getProveedor());
        productoDB.setUmbralBajoStock(producto.getUmbralBajoStock());
        productoRepository.save(productoDB);
    }

    // Actualizar la imagen de un producto o la agrega si no existe
    @Override
    @Transactional
    public void updateImage(MultipartFile file, Long productoId) {

        Producto productoDB = productoRepository.getReferenceById(productoId);
        Imagen imagenActual = productoDB.getImagen();

        if (imagenActual != null) {
            imagenService.deleteFromCloudinary(imagenActual.getImageId());
            imagenActual = imagenService.update(file, imagenActual);
        } else {
            Imagen imagenNueva = imagenService.uploadImage(file);
            productoDB.setImagen(imagenNueva);
        }
        productoRepository.save(productoDB);
    }

    // Eliminar un producto
    @Override
    public void delete(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        // Eliminar la imagen si existe
        if (producto.getImagen() != null) {
            imagenService.completeDeletion(producto.getImagen());
        }

        // Eliminar el producto
        productoRepository.delete(producto);
    }

    // Actualiza el stock y registra el movimiento
    @Override
    @Transactional
    public MovimientoStock updateStock(MovimientoStock movimiento) {

        Producto producto = productoRepository.getReferenceById(movimiento.getProducto().getId());

        Long stockActual = producto.getCantidadStock();
        Long stockMovimiento = movimiento.getCantidad();

        if (movimiento.getTipoMovimiento() == MovimientoStock.TipoMovimiento.SALIDA) {
            if (stockActual < stockMovimiento) {
                throw new IllegalArgumentException("No hay suficiente stock para realizar esta operación");
            }
            movimiento.setCostoAdquisicion(null);
            producto.setCantidadStock(stockActual - stockMovimiento);
        } else {
            if (movimiento.getCostoAdquisicion() == null || movimiento.getCostoAdquisicion() <= 0) {
                throw new IllegalArgumentException("El costo de adquisición es obligatorio para entradas de stock.");
            }
            producto.setCantidadStock(stockActual + stockMovimiento);
        }

        if (producto.getCantidadStock() <= producto.getUmbralBajoStock()) {
            sendNotification("El producto " + producto.getNombre() + " está bajo de stock");
        }

        movimiento.setProducto(producto);
        movimiento.setFechaMovimiento(LocalDateTime.now());

        productoRepository.save(producto);
        return movimientoStockRepository.save(movimiento);
    }

    @Override
    public void sendNotification(String message) {
        notificacionService.sendNotification(message);
    }
}
