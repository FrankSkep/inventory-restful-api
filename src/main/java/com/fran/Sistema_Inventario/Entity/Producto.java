package com.fran.Sistema_Inventario.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(name = "cantidad_stock", nullable = false)
    private Long cantidadStock;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading, para cargar la categoría solo cuando se necesita
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    private String imageUrl;

    private String imageId;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    @JsonBackReference
    private Proveedor proveedor;

    private Integer umbralBajoStock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MovimientoStock> movimientosStock = new ArrayList<>();

    public Producto(Long id, String nombre, String descripcion, Double precio, Long cantidadStock, Categoria categoria, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public Producto(String nombre, String descripcion, Double precio, Long cantidadStock, Categoria categoria, String imageUrl, String imageId, Proveedor proveedor, Integer umbralBajoStock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
        this.proveedor = proveedor;
        this.umbralBajoStock = umbralBajoStock;
    }

    public Producto() {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageId() {return imageId; }

    public void setImageId(String imageId) { this.imageId = imageId; }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
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
