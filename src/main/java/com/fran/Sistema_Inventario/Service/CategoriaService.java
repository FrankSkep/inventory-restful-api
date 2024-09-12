package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Categoria;

import java.util.List;

public interface CategoriaService {

    public Categoria obtenerPorNombre(String nombre);

    public Categoria obtenerPorId(int id);

    public List<Categoria> obtenerTodas();

    public Categoria guardarCategoria(Categoria categoria);

    public void eliminarCategoria(Categoria categoria);
}
