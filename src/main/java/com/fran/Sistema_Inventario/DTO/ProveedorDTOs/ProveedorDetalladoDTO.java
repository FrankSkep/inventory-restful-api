package com.fran.Sistema_Inventario.DTO.ProveedorDTOs;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import java.util.Set;

public class ProveedorDetalladoDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String identificacionFiscal;
    private Set<ProductoBasicoDTO> productos;

    public ProveedorDetalladoDTO(Long id, String nombre, String direccion, String email, String telefono, String identificacionFiscal, Set<ProductoBasicoDTO> productos) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.identificacionFiscal = identificacionFiscal;
        this.productos = productos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificacionFiscal() {
        return identificacionFiscal;
    }

    public void setIdentificacionFiscal(String identificacionFiscal) {
        this.identificacionFiscal = identificacionFiscal;
    }

    public Set<ProductoBasicoDTO> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoBasicoDTO> productos) {
        this.productos = productos;
    }

}
