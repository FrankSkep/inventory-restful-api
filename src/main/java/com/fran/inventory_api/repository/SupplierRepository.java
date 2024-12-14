package com.fran.inventory_api.repository;

import com.fran.inventory_api.dto.SupplierResponseBasic;
import com.fran.inventory_api.dto.SupplierResponseDetailed;
import com.fran.inventory_api.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT new com.fran.inventory_api.dto.Supplier.ProveedorResponseBasic(p.id, p.nombre, p.direccion, p.email, p.telefono, p.identificacionFiscal) FROM Supplier p")
    List<SupplierResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.dto.Supplier.ProveedorResponseDetailed(p.id, p.nombre, p.direccion, p.email, p.telefono, p.identificacionFiscal, " +
            "(SELECT new com.fran.inventory_api.dto.Product.ProductoResponseProv(prod.id, prod.nombre, prod.descripcion, prod.precio, prod.categoria.nombre, prod.imagen.url) " +
            "FROM p.productos prod)) " +
            "FROM Supplier p WHERE p.id = :id")
    SupplierResponseDetailed getDetailsById(Long id);
}