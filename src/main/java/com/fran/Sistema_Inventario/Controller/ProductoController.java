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
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    private final ProductoServiceImpl productoService;
    private CloudinaryServiceImpl cloudinaryService;
    private final ProductoMapperDTO productoMapper;

    public ProductoController(ProductoServiceImpl productoService, CloudinaryServiceImpl cloudinaryService, ProductoMapperDTO productoMapper) {
        this.productoService = productoService;
        this.cloudinaryService = cloudinaryService;
        this.productoMapper = productoMapper;
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
            @RequestPart("file") MultipartFile file,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Verificar si el archivo es valido
        if (!FileValidator.isValidFile(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo no válido");
        }

        try {
            if (!file.isEmpty()) {
                // Subir la imagen y obtener la URL y el public_id
                Map uploadResult = cloudinaryService.uploadFile(file);
                String imageUrl = (String) uploadResult.get("url");
                String imageId = (String) uploadResult.get("public_id");

                productoRequest.setImageUrl(imageUrl);
                productoRequest.setImageId(imageId);
            }
            // Guardar el producto en la base de datos
            Producto producto = productoService.guardarProducto(productoRequest);
            return ResponseEntity.ok(productoMapper.toDTO(producto));
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

        if (nuevaImagenOpcional != null) {
            try {
                productoService.updateFile(productoRequest.getId(), nuevaImagenOpcional);
            } catch (Exception e) {
                System.out.println("Error al subir la imagen: " + e.getMessage());
            }
        }

        productoService.editarProducto(id, productoRequest);
        return ResponseEntity.ok().body("Producto Editado");
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {

        if (productoService.eliminarProducto(id)) {
            return ResponseEntity.ok("Producto eliminado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("No se encontro el producto con id " + id);
        }
    }
}
