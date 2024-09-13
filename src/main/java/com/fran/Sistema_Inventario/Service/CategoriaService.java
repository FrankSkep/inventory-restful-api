package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Categoria;

import java.util.List;

public interface CategoriaService {

    Categoria obtenerPorNombre(String nombre);

    Categoria obtenerPorId(int id);

    List<Categoria> obtenerTodas();

    Categoria guardarCategoria(Categoria categoria);

    void eliminarCategoria(Categoria categoria);
}
