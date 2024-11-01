package com.fran.InventoryAPI.controller;

import com.fran.InventoryAPI.entity.Categoria;
import com.fran.InventoryAPI.service.CategoriaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public List<Categoria> getCategories() {
        return categoriaService.getAll();
    }

    @GetMapping("/{id}")
    public Categoria getCategoryDetails(@PathVariable Long id) {
        return categoriaService.getById(id);
    }

    @PostMapping
    public Categoria addCategory(@RequestBody Categoria categoria) {
        return categoriaService.save(categoria);
    }

    @PutMapping("/{id}")
    public Categoria updateCategory(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.update(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok().body("Categoría eliminada correctamente.");
    }
}
