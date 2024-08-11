package com.fran.Sistema_Inventario.DTO;

import jakarta.validation.constraints.*;

public class ProductoDTO {

    private Integer id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double precio;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en stock debe ser mayor o igual a 0")
    private Integer cantidadStock;

    @NotBlank(message = "Debe seleccionar una categoría")
    private String categoria;

    private String imageUrl;

    @NotNull(message = "Debe seleccionar un proveedor")
    private Integer proveedorId;

    public ProductoDTO(String nombre, String descripcion, Double precio, Integer cantidadStock, String categoria, Integer proveedorId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.proveedorId = proveedorId;
    }

    public ProductoDTO(Integer id, String nombre, String descripcion, Double precio, Integer cantidadStock, String categoria, Integer proveedorId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.proveedorId = proveedorId;
    }

    public ProductoDTO(Integer id, String nombre, String descripcion, Double precio, Integer cantidadStock, String categoria, String imageUrl, Integer proveedorId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
        this.proveedorId = proveedorId;
    }

    public ProductoDTO() {

    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }
}
