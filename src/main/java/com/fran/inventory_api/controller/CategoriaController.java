package com.fran.inventory_api.controller;

import com.fran.inventory_api.entity.Categoria;
import com.fran.inventory_api.service.CategoriaService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> getCategories() {
        return categoriaService.getAll();
    }

    @GetMapping("/{id}")
    public Categoria getCategoryDetails(@PathVariable Long id) {
        return categoriaService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Categoria addCategory(@RequestBody Categoria categoria) {
        return categoriaService.save(categoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Categoria updateCategory(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.update(categoria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok().body("Categoría eliminada correctamente.");
    }
}
