package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProveedorDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProveedorService proveedorService;

    // Obtener lista de todos los proveedores
    @GetMapping({"", "/"})
    public List<Proveedor> obtenerProveedores() {
        return proveedorService.obtenerProveedores();
    }

    // Registrar nuevo proveedor
    @PostMapping("/registrar")
    public ResponseEntity<?> crearProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Proveedor proveedor = new Proveedor(
                proveedorDTO.getNombre(),
                proveedorDTO.getDireccion(),
                proveedorDTO.getEmail(),
                proveedorDTO.getTelefono(),
                proveedorDTO.getIdentificacionFiscal()
        );

        proveedorService.registrarProveedor(proveedor);
        return ResponseEntity.ok(proveedor);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarDatosProveedor(@PathVariable Integer id, @Valid @RequestBody ProveedorDTO proveedor, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        return ResponseEntity.ok(proveedorService.editarProveedor(id, proveedor));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Integer id) {

        if (proveedorService.eliminarProveedor(id)) {
            return ResponseEntity.ok("Proveedor eliminado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("No se encontro el producto con id " + id);
        }

    }
}
