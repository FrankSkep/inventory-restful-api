package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProveedorDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping({"", "/"})

    public List<Proveedor> obtenerProbedores() {
        return proveedorService.obtenerProveedores();
    }

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

}
