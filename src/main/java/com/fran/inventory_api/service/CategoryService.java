package com.fran.inventory_api.service;

import com.fran.inventory_api.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getByName(String nombre);

    Category getById(Long id);

    List<Category> getAll();

    Category save(Category category);

    Category update(Category category);

    void delete(Long id);
}
