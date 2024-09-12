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

    @Override
    public Optional<Producto> obtenerPorID(Long id) throws IOException {
        return productoRepository.findById(id);
    }

    // Guardar un nuevo producto
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
    public void actualizarProducto(Producto productoReq) {
        System.out.println("ID del prod: " + productoReq.getId());
        Producto productoDB = productoRepository.getReferenceById(productoReq.getId());

        productoDB.setNombre(productoReq.getNombre());
        productoDB.setDescripcion(productoReq.getDescripcion());
        productoDB.setPrecio(productoReq.getPrecio());
        productoDB.setCategoria(productoReq.getCategoria());
        productoDB.setProveedor(productoReq.getProveedor());
        productoDB.setUmbralBajoStock(productoReq.getUmbralBajoStock());
        productoRepository.save(productoDB);
    }

    @Override
    public void actualizarImagenProducto(MultipartFile file, Producto producto) throws IOException {
        if (producto.getImagen() != null) {
            imagenService.deleteImage(producto.getImagen());
        }
        Imagen newImage = imagenService.uploadImage(file);
        producto.setImagen(newImage);
        productoRepository.save(producto);
    }

    // Eliminar un producto
    @Override
    public void eliminarProducto(Producto producto) throws IOException {

        // Eliminar la imagen
        imagenService.deleteImage(producto.getImagen());

        // Eliminar el producto
        productoRepository.deleteById(producto.getId());
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
