package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.entity.Category;
import com.fran.inventory_api.exception.CategoryNotFoundException;
import com.fran.inventory_api.repository.CategoryRepository;
import com.fran.inventory_api.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getByName(String nombre) {
        return categoryRepository.findByNombre(nombre);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category dbCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        dbCategory.setNombre(category.getNombre());

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
