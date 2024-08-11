package com.fran.Sistema_Inventario.DTO;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import java.util.ArrayList;
import java.util.List;

public class ProductoDetalladoDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Long cantidadStock;

    private String categoria;

    private String imageUrl;

    private ProveedorBasicoDTO proveedor;

    private Integer umbralBajoStock;

    private List<MovimientoStock> movimientosStock = new ArrayList<>();

    public ProductoDetalladoDTO(Long id, String nombre, String descripcion, Double precio, Long cantidadStock, String categoria, String imageUrl, ProveedorBasicoDTO proveedor, Integer umbralBajoStock, List<MovimientoStock> movimientos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
        this.proveedor = proveedor;
        this.umbralBajoStock = umbralBajoStock;
        this.movimientosStock = movimientos;
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

    public ProveedorBasicoDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorBasicoDTO proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getUmbralBajoStock() {
        return umbralBajoStock;
    }

    public void setUmbralBajoStock(Integer umbralBajoStock) {
        this.umbralBajoStock = umbralBajoStock;
    }

    public List<MovimientoStock> getMovimientosStock() {
        return movimientosStock;
    }

    public void setMovimientosStock(List<MovimientoStock> movimientosStock) {
        this.movimientosStock = movimientosStock;
    }

}
