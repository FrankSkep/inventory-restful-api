package com.fran.Sistema_Inventario.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = false)
    private Categoria categoria;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Imagen imagen;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Proveedor proveedor;

    private Integer umbralBajoStock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MovimientoStock> movimientosStock = new ArrayList<>();

    // Constructor para agregar nuevo producto
    public Producto(String nombre, String descripcion, Double precio, Long cantidadStock, Categoria categoria, Proveedor proveedor, Integer umbralBajoStock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.umbralBajoStock = umbralBajoStock;
    }

    // Constructor para mapear DTO a Entidad
    public Producto(Long id, String nombre, String descripcion, Double precio, Long cantidadStock, Categoria categoria, Proveedor proveedor, Integer umbralBajoStock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.umbralBajoStock = umbralBajoStock;
    }

    public Producto() {
    }
}
