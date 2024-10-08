package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria getByName(String nombre);

    Categoria getById(int id);

    List<Categoria> getAll();

    Categoria save(Categoria categoria);

    void delete(Categoria categoria);
}
