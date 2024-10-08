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
    public Categoria getByName(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    @Override
    public Categoria getById(int id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void delete(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }
}
