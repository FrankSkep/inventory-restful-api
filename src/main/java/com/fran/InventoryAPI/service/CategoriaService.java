package com.fran.InventoryAPI.service;

import com.fran.InventoryAPI.entity.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria getByName(String nombre);

    Categoria getById(Long id);

    List<Categoria> getAll();

    Categoria save(Categoria categoria);

    Categoria update(Categoria categoria);

    void delete(Long id);
}
