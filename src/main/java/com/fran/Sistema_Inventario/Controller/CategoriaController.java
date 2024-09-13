package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.Entity.Categoria;
import com.fran.Sistema_Inventario.Service.Impl.CategoriaServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaServiceImpl categoriaService;

    public CategoriaController(CategoriaServiceImpl categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public List<Categoria> obtenerCategorias() {
        return categoriaService.obtenerTodas();
    }

    @PostMapping("/agregar")
    public Categoria agregarCategoria(@RequestBody Categoria categoria) {
        return categoriaService.guardarCategoria(categoria);
    }
}
