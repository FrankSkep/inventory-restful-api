package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Categoria;

import java.util.List;

public interface CategoriaService {
    public Categoria getCategoriaByNombre(String nombre);
    public Categoria getCategoriaById(int id);
    public List<Categoria> getAllCategorias();
}
