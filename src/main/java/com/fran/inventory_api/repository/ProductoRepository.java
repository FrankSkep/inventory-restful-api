package com.fran.inventory_api.repository;

import com.fran.inventory_api.dto.Producto.ProductoResponseBasic;
import com.fran.inventory_api.entity.Categoria;
import com.fran.inventory_api.entity.Producto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByPrecioBetween(Double min, Double max);

    @Query("SELECT new com.fran.inventory_api.dto.Producto.ProductoResponseBasic(p.id, p.nombre, p.descripcion, p.precio, p.cantidadStock, p.categoria.nombre, p.imagen.url) FROM Producto p")
    List<ProductoResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.dto.Producto.ProductoResponseBasic(p.id, p.nombre, p.descripcion, p.precio, p.cantidadStock, p.categoria.nombre, p.imagen.url) FROM Producto p")
    Page<ProductoResponseBasic> findAllBasic(Pageable pageable);
}
