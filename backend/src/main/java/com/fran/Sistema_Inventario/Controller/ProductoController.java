package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Service.Impl.ProductoServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productoService;

    // Obtener todos los productos del inventario
    @GetMapping("/")
    public List<Producto> listaProductos() {
        return productoService.obtenerProductos();
    }

    // Ver detalles de un producto
    @GetMapping("/detalles/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.obtenerPorID(id));
    }

    // Agregar nuevo producto al inventario
    @PostMapping("/agregar")
    public ResponseEntity<?> crearProducto(@Valid @RequestBody ProductoDTO productoRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Producto producto = productoService.guardarProducto(productoRequest);
        return ResponseEntity.ok(producto);
    }

    // Editar datos de un producto
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Integer id, @Valid @RequestBody ProductoDTO productoRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return ResponseEntity.ok(productoService.editarProducto(id, productoRequest));
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {

        if (productoService.eliminarProducto(id)) {
            return ResponseEntity.ok("Producto eliminado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("No se encontro el producto con id " + id);
        }
    }
}
