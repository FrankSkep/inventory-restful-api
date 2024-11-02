package com.fran.inventory_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String direccion;

    private String email;

    private String telefono;

    private String identificacionFiscal;

    // Relación con productos
    @OneToMany(mappedBy = "proveedor")
    @JsonManagedReference
    private Set<Producto> productos;

    public Proveedor() {
    }

    public Proveedor(Long id, String nombre, String direccion, String email, String telefono, String identificacionFiscal) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.identificacionFiscal = identificacionFiscal;
    }

    public Proveedor(String nombre, String direccion, String email, String telefono, String identificacionFiscal) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.identificacionFiscal = identificacionFiscal;
    }

}
