package com.fran.inventory_api.system.service.Impl;

import com.fran.inventory_api.system.entity.Category;
import com.fran.inventory_api.system.exception.CategoryNotFoundException;
import com.fran.inventory_api.system.repository.CategoryRepository;
import com.fran.inventory_api.system.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryByName(String nombre) {
        return categoryRepository.findByName(nombre);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category dbCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        dbCategory.setName(category.getName());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
