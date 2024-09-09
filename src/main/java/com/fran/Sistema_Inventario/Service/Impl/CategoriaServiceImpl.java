package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.Entity.Categoria;
import com.fran.Sistema_Inventario.Repository.CategoriaRepository;
import com.fran.Sistema_Inventario.Service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    private CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria getCategoriaByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    public Categoria getCategoriaById(int id) {
        return categoriaRepository.findById(id);
    }

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }
}
