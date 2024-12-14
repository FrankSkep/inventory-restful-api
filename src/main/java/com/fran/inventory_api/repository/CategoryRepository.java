package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByNombre(String nombre);

    public Category findById(long id);

    public List<Category> findByNombreContaining(String nombre);

}
