package com.fran.inventory_api.repository;

import com.fran.inventory_api.dto.Producto.ProductoResponseProv;
import com.fran.inventory_api.dto.Proveedor.ProveedorResponseBasic;
import com.fran.inventory_api.dto.Proveedor.ProveedorResponseDetailed;
import com.fran.inventory_api.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Query("SELECT new com.fran.inventory_api.dto.Proveedor.ProveedorResponseBasic(p.id, p.nombre, p.direccion, p.email, p.telefono, p.identificacionFiscal) FROM Proveedor p")
    List<ProveedorResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.dto.Proveedor.ProveedorResponseDetailed(p.id, p.nombre, p.direccion, p.email, p.telefono, p.identificacionFiscal, " +
            "(SELECT new com.fran.inventory_api.dto.Producto.ProductoResponseProv(prod.id, prod.nombre, prod.descripcion, prod.precio, prod.categoria.nombre, prod.imagen.url) " +
            "FROM p.productos prod)) " +
            "FROM Proveedor p WHERE p.id = :id")
    ProveedorResponseDetailed getDetailsById(Long id);
}