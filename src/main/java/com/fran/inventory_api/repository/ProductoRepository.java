package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Categoria;
import com.fran.inventory_api.entity.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByPrecioBetween(Double min, Double max);
}
