package com.fran.inventory_api.system.repository;

import com.fran.inventory_api.system.dto.ProductResponseSupplier;
import com.fran.inventory_api.system.dto.SupplierResponseBasic;
import com.fran.inventory_api.system.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT new com.fran.inventory_api.system.dto.SupplierResponseBasic(p.id, p.name, p.address, p.email, p.phone, p.taxIdentification) FROM Supplier p")
    List<SupplierResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.system.dto.ProductResponseSupplier(p.id, p.name, p.description, c.name) " +
            "FROM Product p " +
            "JOIN p.category c " +
            "LEFT JOIN p.image i " +
            "WHERE p.supplier.id = :supplierId")
    List<ProductResponseSupplier> findProductsBySupplierId(Long supplierId);
}