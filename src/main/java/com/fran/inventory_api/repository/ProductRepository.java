package com.fran.inventory_api.repository;

import com.fran.inventory_api.dto.ProductResponseBasic;
import com.fran.inventory_api.entity.Category;
import com.fran.inventory_api.entity.Product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNombreContaining(String nombre);

    List<Product> findByCategoria(Category category);

    List<Product> findByPrecioBetween(Double min, Double max);

    @Query("SELECT new com.fran.inventory_api.dto.Product.ProductoResponseBasic(p.id, p.nombre, p.descripcion, p.precio, p.cantidadStock, p.categoria.nombre, p.imagen.url) FROM Product p")
    List<ProductResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.dto.Product.ProductoResponseBasic(p.id, p.nombre, p.descripcion, p.precio, p.cantidadStock, p.categoria.nombre, p.imagen.url) FROM Product p")
    Page<ProductResponseBasic> findAllBasic(Pageable pageable);
}
