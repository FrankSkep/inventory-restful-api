package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProductoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Service.ProductoServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/")
    public List<Producto> listaProductos() {
        return productoService.obtenerProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.obtenerPorID(id));
    }

    @PostMapping("/agregar-producto")
    public ResponseEntity<?> crearProducto(@Valid @RequestBody ProductoDTO productoRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        productoService.guardarProducto(productoRequest);
        return ResponseEntity.ok("Producto creado exitosamente");
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Integer id, @Valid @RequestBody ProductoDTO productoRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return ResponseEntity.ok(productoService.editarProducto(id, productoRequest));
    }
}
