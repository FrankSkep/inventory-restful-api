package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.MapperDTO.ProductoMapperDTO;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Utils.FileValidator;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapperDTO productoMapper;

    public ProductoController(ProductoService productoService, ProductoMapperDTO productoMapper) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
    }

    // Obtener todos los productos del inventario
    @GetMapping("/")
    public List<ProductoBasicoDTO> listaProductos() {
        return productoService.obtenerProductos();
    }

    // Obtener detalles de un producto
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

        Producto producto = productoService.guardarProducto(
                productoMapper.toEntity(productoRequest),
                FileValidator.isValidFile(file) ? file : null);

        return ResponseEntity.ok(productoMapper.toDTO(producto));
    }

    // Editar datos de un producto
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Long id, @ModelAttribute ProductoDTO productoRequest,
                                            @RequestPart(value = "file", required = false) MultipartFile nuevaImagenOpcional,
                                            BindingResult result) throws IOException {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Actualiza datos del producto
        productoService.actualizarProducto(productoMapper.toEntityWithId(productoRequest));

        // Si se recibe una imagen, la actualiza
        if (FileValidator.isValidFile(nuevaImagenOpcional)) {
            productoService.actualizarImagenProducto(nuevaImagenOpcional, productoRequest.getId());
        }

        return ResponseEntity.ok().body("Producto Editado");
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado exitosamente");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto con ID " + id + " no encontrado.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al eliminar la imagen del producto.");
        }
    }
}
