package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
