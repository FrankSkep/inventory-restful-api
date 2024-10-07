package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import com.fran.Sistema_Inventario.MapperDTO.ProveedorMapperDTO;
import com.fran.Sistema_Inventario.Service.ProveedorService;
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
    public List<ProveedorBasicoDTO> getSuppliers() {
        return proveedorService.obtenerProveedores();
    }

    // Detalles de un proveedor
    @GetMapping("/detalles/{id}")
    public ProveedorDetalladoDTO supplierDetails(@PathVariable Long id) {
        return proveedorService.detallesProveedor(proveedorService.obtenerPorID(id));
    }

    // Registrar nuevo proveedor
    @PostMapping("/registrar")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody ProveedorDTO supplierRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Proveedor supplier = proveedorMapper.toEntityFromDTO(supplierRequest);

        proveedorService.registrarProveedor(supplier);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @Valid @RequestBody ProveedorDTO supplier, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return ResponseEntity.ok(proveedorService.editarProveedor(proveedorMapper.toEntityFromDTOWithId(supplier)));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {

        proveedorService.delete(id);
        return ResponseEntity.ok().body("Producto eliminado correctamente.");
    }
}
