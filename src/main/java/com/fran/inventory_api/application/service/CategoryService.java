package com.fran.inventory_api.application.service;

import com.fran.inventory_api.application.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryByName(String nombre);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}
