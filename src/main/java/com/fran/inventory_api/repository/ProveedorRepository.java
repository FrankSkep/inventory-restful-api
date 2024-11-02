package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}
