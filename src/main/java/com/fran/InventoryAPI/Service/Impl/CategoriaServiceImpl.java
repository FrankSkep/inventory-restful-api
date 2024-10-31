package com.fran.InventoryAPI.Service.Impl;

import com.fran.InventoryAPI.Entity.Categoria;
import com.fran.InventoryAPI.Repository.CategoriaRepository;
import com.fran.InventoryAPI.Service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
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
    public Categoria getById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
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
    public Categoria update(Categoria categoria) {

        Categoria dbCategory = categoriaRepository.findById(categoria.getId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        dbCategory.setNombre(categoria.getNombre());

        return categoriaRepository.save(categoria);
    }

    @Override
    public void delete(Long id) {
        Categoria category = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        categoriaRepository.delete(category);
    }
}
