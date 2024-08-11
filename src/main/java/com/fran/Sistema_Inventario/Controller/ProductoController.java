package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Service.Impl.FileUploadService;
import com.fran.Sistema_Inventario.Service.Impl.ProductoServiceImpl;
import com.fran.Sistema_Inventario.Utils.FileValidator;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    private final ProductoServiceImpl productoService;
    private final FileUploadService fileUploadService;

    @Autowired
    public ProductoController(ProductoServiceImpl productoService, FileUploadService fileUploadService) {
        this.productoService = productoService;
        this.fileUploadService = fileUploadService;
    }

    // Obtener todos los productos del inventario
    @GetMapping("/")
    public List<Producto> listaProductos() {
        return productoService.obtenerProductos();
    }

    // Ver detalles de un producto
    @GetMapping("/detalles/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorID(id));
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
            // Subir la imagen a Firebase y obtener la URL
            String imageUrl = fileUploadService.uploadFile(file);
            productoRequest.setImageUrl(imageUrl);

            // Guardar el producto en la base de datos
            Producto producto = productoService.guardarProducto(productoRequest);
            return ResponseEntity.ok(new ProductoDTO(producto.getId(), producto.getNombre(),
                    producto.getDescripcion(), producto.getPrecio(), producto.getCantidadStock(),
                    producto.getCategoria(), producto.getImageUrl(), producto.getProveedor().getId(),
                    producto.getUmbralBajoStock()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }

    // Editar datos de un producto
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return ResponseEntity.ok(productoService.editarProducto(id, productoRequest));
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
