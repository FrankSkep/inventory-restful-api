package com.fran.Sistema_Inventario.DTO.ProductoDTOs;

import jakarta.validation.constraints.*;

public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double precio;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en stock debe ser mayor o igual a 0")
    private Long cantidadStock;

    @NotBlank(message = "Debe seleccionar una categoría")
    private String categoria;

    private String imageUrl;

    private String imageId;

    @NotNull(message = "Debe seleccionar un proveedor")
    private Long proveedorId;

    @NotNull(message = "Debe especificar el umbral de alerta de stock")
    @Min(value = 0, message = "El umbral de alerta debe ser mayor o igual a 0")
    private Integer umbralBajoStock;

    public ProductoDTO(String nombre, String descripcion, Double precio, Long cantidadStock, String categoria, Long proveedorId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.proveedorId = proveedorId;
    }

    public ProductoDTO(Long id, String nombre, String descripcion, Double precio, Long cantidadStock, String categoria, Long proveedorId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.proveedorId = proveedorId;
    }

    public ProductoDTO(Long id, String nombre, String descripcion, Double precio, Long cantidadStock, String categoria, String imageUrl, String imageId, Long proveedorId, Integer umbralBajoStock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
        this.proveedorId = proveedorId;
        this.umbralBajoStock = umbralBajoStock;
    }

    public ProductoDTO() {
    }

    // Getters y Setters
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

    public Long getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Long cantidadStock) {
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

    public String getImageId() {return imageId; }

    public void setImageId(String imageId) { this.imageId = imageId;}

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Integer getUmbralBajoStock() {
        return umbralBajoStock;
    }

    public void setUmbralBajoStock(Integer umbralStock) {
        this.umbralBajoStock = umbralStock;
    }
}
