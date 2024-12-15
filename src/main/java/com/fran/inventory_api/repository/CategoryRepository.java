package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByNombre(String nombre);

    Category findById(long id);

    List<Category> findByNombreContaining(String nombre);

}
