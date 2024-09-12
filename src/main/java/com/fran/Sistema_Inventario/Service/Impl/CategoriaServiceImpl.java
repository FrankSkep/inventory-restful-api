package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.Entity.Categoria;
import com.fran.Sistema_Inventario.Repository.CategoriaRepository;
import com.fran.Sistema_Inventario.Service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria obtenerPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    @Override
    public Categoria obtenerPorId(int id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }
}
