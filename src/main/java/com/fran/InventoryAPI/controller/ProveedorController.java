package com.fran.InventoryAPI.controller;

import com.fran.InventoryAPI.dto.Proveedor.ProveedorResponseBasic;
import com.fran.InventoryAPI.dto.Proveedor.ProveedorRequest;
import com.fran.InventoryAPI.dto.Proveedor.ProveedorResponseDetailed;
import com.fran.InventoryAPI.entity.Proveedor;
import com.fran.InventoryAPI.dto_mapper.ProveedorMapperDTO;
import com.fran.InventoryAPI.service.ProveedorService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final ProveedorMapperDTO proveedorMapper;

    public ProveedorController(ProveedorService proveedorService, ProveedorMapperDTO proveedorMapper) {
        this.proveedorService = proveedorService;
        this.proveedorMapper = proveedorMapper;
    }

    // Obtener lista de todos los proveedores
    @GetMapping({"", "/"})
    public List<ProveedorResponseBasic> getSuppliers() {
        return proveedorService.obtenerProveedores();
    }

    // Detalles de un proveedor
    @GetMapping("/detalles/{id}")
    public ProveedorResponseDetailed supplierDetails(@PathVariable Long id) {
        return proveedorService.getDetails(proveedorService.getById(id));
    }

    // Registrar nuevo proveedor
    @PostMapping("/registrar")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody ProveedorRequest supplierRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Proveedor supplier = proveedorMapper.toEntity(supplierRequest);

        proveedorService.save(supplier);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @Valid @RequestBody ProveedorRequest supplier, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return ResponseEntity.ok(proveedorService.update(proveedorMapper.toEntityFromDTOWithId(supplier)));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {

        proveedorService.delete(id);
        return ResponseEntity.ok().body("Producto eliminado correctamente.");
    }
}
