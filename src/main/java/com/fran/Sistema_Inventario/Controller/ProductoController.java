package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.MapperDTO.ProductoMapperDTO;
import com.fran.Sistema_Inventario.Service.Impl.CloudinaryServiceImpl;
import com.fran.Sistema_Inventario.Service.Impl.ProductoServiceImpl;
import com.fran.Sistema_Inventario.Utils.FileValidator;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    private final ProductoServiceImpl productoService;
    private final ProductoServiceImpl productoServiceImpl;
    private final ProductoMapperDTO productoMapper;

    public ProductoController(ProductoServiceImpl productoService, ProductoMapperDTO productoMapper, ProductoServiceImpl productoServiceImpl) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
        this.productoServiceImpl = productoServiceImpl;
    }

    // Obtener todos los productos del inventario
    @GetMapping("/")
    public List<ProductoBasicoDTO> listaProductos() {
        return productoService.obtenerProductos();
    }

    // Ver detalles de un producto
    @GetMapping("/detalles/{id}")
    public ResponseEntity<ProductoDetalladoDTO> detallesProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.detallesProducto(id));
    }

    // Agregar nuevo producto al inventario
    @PostMapping("/agregar")
    public ResponseEntity<?> crearProducto(
            @Valid @ModelAttribute ProductoDTO productoRequest,
            @RequestPart(value = "file", required = false) MultipartFile file,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            // Guardar el producto en la base de datos
            Producto producto = productoService.guardarProducto(productoMapper.toEntity(productoRequest), FileValidator.isValidFile(file) ? file : null);
            return ResponseEntity.ok(productoMapper.toDTO(producto));
//            // Verificar si el archivo es válido
//            if (FileValidator.isValidFile(file)) {
//                // Guardar el producto en la base de datos
//                Producto producto = productoService.guardarProducto(productoMapper.toEntity(productoRequest), file);
//                return ResponseEntity.ok(productoMapper.toDTO(producto));
//            } else {
//                Producto producto = productoService.guardarProducto(productoMapper.toEntity(productoRequest), file);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo no válido");
//            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }

    // Editar datos de un producto
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Long id, @ModelAttribute ProductoDTO productoRequest,
            @RequestPart(value = "file", required = false) MultipartFile nuevaImagenOpcional,
            BindingResult result) throws IOException {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        productoService.actualizarProducto(productoMapper.toEntityWithId(productoRequest));

        System.out.println("Paso de actualizar producto");

        if (nuevaImagenOpcional != null) {
            Optional<Producto> productoEntity = productoService.obtenerPorID(productoRequest.getId());

            if (FileValidator.isValidFile(nuevaImagenOpcional)) {
                try {
                    productoService.actualizarImagenProducto(nuevaImagenOpcional, productoEntity.get());
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }

        return ResponseEntity.ok().body("Producto Editado");
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) throws IOException {

        Optional<Producto> producto = productoServiceImpl.obtenerPorID(id);
        if (producto.isPresent()) {
            productoServiceImpl.eliminarProducto(producto.get());
            return ResponseEntity.ok("Producto eliminado");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
