package com.fran.inventory_api.system.controller;

import com.fran.inventory_api.system.dto.SupplierResponseBasic;
import com.fran.inventory_api.system.dto.SupplierResponseDetailed;
import com.fran.inventory_api.system.entity.Supplier;
import com.fran.inventory_api.system.mapper.SupplierMapper;
import com.fran.inventory_api.system.dto.SupplierRequest;
import com.fran.inventory_api.system.service.SupplierService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<SupplierResponseBasic> getSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDetailed> supplierDetails(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getDetails(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequest supplierRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Supplier supplier = supplierMapper.toEntity(supplierRequest);

        supplierService.createSupplier(supplier);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierRequest supplier, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return ResponseEntity.ok(supplierService.updateSupplier(supplierMapper.toEntityFromDTOWithId(supplier)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {

        supplierService.deleteSupplier(id);
        return ResponseEntity.ok().body("Producto eliminado correctamente.");
    }
}
