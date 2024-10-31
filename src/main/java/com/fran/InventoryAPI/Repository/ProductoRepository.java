package com.fran.InventoryAPI.Repository;

import com.fran.InventoryAPI.Entity.Categoria;
import com.fran.InventoryAPI.Entity.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByPrecioBetween(Double min, Double max);
}
