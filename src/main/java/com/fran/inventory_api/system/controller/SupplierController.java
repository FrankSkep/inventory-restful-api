package com.fran.inventory_api.system.controller;

import com.fran.inventory_api.system.dto.SupplierRequest;
import com.fran.inventory_api.system.dto.SupplierResponseBasic;
import com.fran.inventory_api.system.dto.SupplierResponseDetailed;
import com.fran.inventory_api.system.entity.Supplier;
import com.fran.inventory_api.system.mapper.SupplierMapper;
import com.fran.inventory_api.system.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    public SupplierController(SupplierService supplierService, SupplierMapper supplierMapper) {
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponseBasic>> getSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDetailed> supplierDetails(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getDetails(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<?> createSupplier(@RequestBody SupplierRequest supplierRequest) {

        Supplier supplier = supplierMapper.toEntity(supplierRequest);

        Supplier createdSupplier = supplierService.createSupplier(supplier);
        return ResponseEntity.created(URI.create("/api/suppliers/" + createdSupplier.getId())).body(createdSupplier);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody SupplierRequest supplier) {
        Supplier sup = supplierService.updateSupplier(supplierMapper.toEntityFromDTOWithId(supplier));
        return ResponseEntity.ok(sup);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {

        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
