package com.fran.InventoryAPI.Service;

import com.fran.InventoryAPI.Entity.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria getByName(String nombre);

    Categoria getById(Long id);

    List<Categoria> getAll();

    Categoria save(Categoria categoria);

    Categoria update(Categoria categoria);

    void delete(Long id);
}
