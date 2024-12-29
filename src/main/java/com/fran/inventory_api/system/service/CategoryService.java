package com.fran.inventory_api.system.service;

import com.fran.inventory_api.system.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryByName(String nombre);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}
