package com.fran.Sistema_Inventario.Repository;

import com.fran.Sistema_Inventario.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public Categoria findByNombre(String nombre);

    public Categoria findById(long id);

    public List<Categoria> findByNombreContaining(String nombre);

}
