package com.fran.InventoryAPI.Controller;

import com.fran.InventoryAPI.Entity.Categoria;
import com.fran.InventoryAPI.Service.CategoriaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public List<Categoria> getCategories() {
        return categoriaService.getAll();
    }

    @GetMapping("/detalles/{id}")
    public Categoria getCategoryDetails(@PathVariable Long id) {
        return categoriaService.getById(id);
    }

    @PostMapping("/registrar")
    public Categoria addCategory(@RequestBody Categoria categoria) {
        return categoriaService.save(categoria);
    }

    @PutMapping("/editar/{id}")
    public Categoria updateCategory(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.update(categoria);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok().body("Categoría eliminada correctamente.");
    }
}
