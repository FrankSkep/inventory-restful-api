package com.fran.Sistema_Inventario.Repository;

import com.fran.Sistema_Inventario.Entity.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByPrecioBetween(Double min, Double max);
}
