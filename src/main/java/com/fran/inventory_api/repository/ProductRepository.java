package com.fran.inventory_api.repository;

import com.fran.inventory_api.dto.ProductResponseBasic;
import com.fran.inventory_api.entity.Category;
import com.fran.inventory_api.entity.Product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNombreContaining(String nombre);

    List<Product> findByCategory(Category category);

    List<Product> findByPrecioBetween(Double min, Double max);

    @Query("SELECT new com.fran.inventory_api.dto.ProductResponseBasic(p.id, p.name, p.description, p.price, p.stock, p.category.name, p.image.url) FROM Product p")
    List<ProductResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.dto.ProductResponseBasic(p.id, p.name, p.description, p.price, p.stock, p.category.name, p.image.url) FROM Product p")
    Page<ProductResponseBasic> findAllBasic(Pageable pageable);
}
