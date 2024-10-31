package com.fran.InventoryAPI.Controller;

import com.fran.InventoryAPI.Entity.Categoria;
import com.fran.InventoryAPI.Service.CategoriaService;

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

    @PostMapping("/nueva")
    public Categoria addCategory(@RequestBody Categoria categoria) {
        return categoriaService.save(categoria);
    }
}
