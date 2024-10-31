package com.fran.InventoryAPI.Service;

import com.fran.InventoryAPI.Entity.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria getByName(String nombre);

    Categoria getById(int id);

    List<Categoria> getAll();

    Categoria save(Categoria categoria);

    void delete(Categoria categoria);
}
