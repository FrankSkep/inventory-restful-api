package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Imagen;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.MapperDTO.ProductoMapperDTO;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoStockRepository movimientoStockRepository;
    private final ProductoMapperDTO productoMapper;
    private final ImagenServiceImpl imagenService;

    public ProductoServiceImpl(ProductoRepository productoRepository, MovimientoStockRepository movimientoStockRepository,
            ProveedorService proveedorService, ProductoMapperDTO productoMapper, ImagenServiceImpl imagenService,
            CategoriaServiceImpl categoriaService) {
        this.productoRepository = productoRepository;
        this.movimientoStockRepository = movimientoStockRepository;
        this.productoMapper = productoMapper;
        this.imagenService = imagenService;
    }

    // Obtener todos los productos y sus datos basicos
    @Override
    public List<ProductoBasicoDTO> obtenerProductos() {

        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(productoMapper::toDTObasic).collect(Collectors.toList());
    }

    // Obtener detalles de un producto
    @Override
    public ProductoDetalladoDTO detallesProducto(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        return productoMapper.toDTOdetailed(producto);
    }

    // Obtener una entidad Producto por su id
    @Override
    public Optional<Producto> obtenerPorID(Long id) throws IOException {
        return productoRepository.findById(id);
    }

    // Guardar un nuevo producto
    @Transactional
    @Override
    public Producto guardarProducto(Producto producto, MultipartFile file) throws IOException {

        if (file != null) {
            Imagen imagen = imagenService.uploadImage(file);
            producto.setImagen(imagen);
        }
        return productoRepository.save(producto);
    }

    // Editar datos de un producto existente
    @Override
    public void actualizarProducto(Producto producto) {

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
    public void actualizarImagenProducto(MultipartFile file, Long productoId) throws IOException {

        Producto productoDB = productoRepository.getReferenceById(productoId);
        Imagen imagenActual = productoDB.getImagen();

        if (imagenActual != null) {
            imagenService.eliminarImagenCloudinary(imagenActual.getImageId());
            imagenActual = imagenService.actualizarImagen(file, imagenActual);
//            productoDB.setImagen(imagenActual);
        } else {
            Imagen imagenNueva = imagenService.uploadImage(file);
            productoDB.setImagen(imagenNueva);
        }
        productoRepository.save(productoDB);
    }

    // Eliminar un producto
    @Override
    public void eliminarProducto(Long id) throws IOException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        // Eliminar la imagen si existe
        if (producto.getImagen() != null) {
            imagenService.eliminarImagenCompleta(producto.getImagen());
        }

        // Eliminar el producto
        productoRepository.delete(producto);
    }

    // Actualiza el stock y registra el movimiento
    @Override
    @Transactional
    public MovimientoStock actualizarStock(MovimientoStock movimiento) {

        Producto producto = productoRepository.getReferenceById(movimiento.getProducto().getId());

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
