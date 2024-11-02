package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public Categoria findByNombre(String nombre);

    public Categoria findById(long id);

    public List<Categoria> findByNombreContaining(String nombre);

}
