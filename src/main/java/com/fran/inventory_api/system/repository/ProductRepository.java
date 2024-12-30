package com.fran.inventory_api.system.repository;

import com.fran.inventory_api.system.dto.ProductResponseBasic;
import com.fran.inventory_api.system.entity.Category;
import com.fran.inventory_api.system.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String nombre);

    List<Product> findByCategory(Category category);

    List<Product> findByPriceBetween(Double min, Double max);

    @Query("SELECT new com.fran.inventory_api.system.dto.ProductResponseBasic(p.id, p.name, p.description, p.price, p.stock, p.category.name, p.image.url) FROM Product p")
    List<ProductResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.system.dto.ProductResponseBasic(p.id, p.name, p.description, p.price, p.stock, p.category.name, p.image.url) FROM Product p")
    Page<ProductResponseBasic> findAllBasic(Pageable pageable);
}
