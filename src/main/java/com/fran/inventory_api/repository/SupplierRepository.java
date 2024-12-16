package com.fran.inventory_api.repository;

import com.fran.inventory_api.dto.SupplierResponseBasic;
import com.fran.inventory_api.dto.SupplierResponseDetailed;
import com.fran.inventory_api.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT new com.fran.inventory_api.dto.SupplierResponseBasic(p.id, p.name, p.address, p.email, p.phone, p.taxIdentification) FROM Supplier p")
    List<SupplierResponseBasic> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.dto.SupplierResponseDetailed(p.id, p.name, p.address, p.email, p.phone, p.taxIdentification, " +
            "(SELECT new com.fran.inventory_api.dto.ProductResponseProv(prod.id, prod.name, prod.description, prod.price, prod.category.name, prod.image.url) " +
            "FROM Supplier s JOIN s.products prod WHERE s.id = p.id)) " +
            "FROM Supplier p WHERE p.id = :id")
    SupplierResponseDetailed getDetailsById(Long id);
}