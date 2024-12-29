package com.fran.inventory_api.system.repository;

import com.fran.inventory_api.system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String nombre);

    Category findById(long id);

    List<Category> findByNameContaining(String nombre);

}
