package com.fran.inventory_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "precio_venta", nullable = false)
    private Double precio;

    @Column(name = "cantidad_stock", nullable = false)
    private Long cantidadStock;

    @ManyToOne(fetch = FetchType.LAZY)
    // Lazy loading, para cargar la categoría solo cuando se necesita
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Supplier supplier;

    @Column(name = "stock_minimo", nullable = false)
    private Integer umbralBajoStock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Movement> movimientosStock;

    // Constructor para agregar nuevo producto
    public Product(String nombre, String descripcion, Double precio, Long cantidadStock, Category category, Supplier supplier, Integer umbralBajoStock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.category = category;
        this.supplier = supplier;
        this.umbralBajoStock = umbralBajoStock;
    }

    // Constructor para mapear DTO a Entidad
    public Product(Long id, String nombre, String descripcion, Double precio, Long cantidadStock, Category category, Supplier supplier, Integer umbralBajoStock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.category = category;
        this.supplier = supplier;
        this.umbralBajoStock = umbralBajoStock;
    }

    public Product() {}
}
