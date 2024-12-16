package com.fran.inventory_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sale_price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @ManyToOne(fetch = FetchType.LAZY)
    // Lazy loading, para cargar la categoría solo cuando se necesita
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Supplier supplier;

    @Column(name = "min_stock", nullable = false)
    private Integer minStock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Movement> stockMovements;

    // Constructor para agregar nuevo producto
    public Product(String name, String description, Double price, Long stock, Category category, Supplier supplier, Integer minStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.supplier = supplier;
        this.minStock = minStock;
    }

    // Constructor para mapear DTO a Entidad
    public Product(Long id, String name, String description, Double price, Long stock, Category category, Supplier supplier, Integer minStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.supplier = supplier;
        this.minStock = minStock;
    }

    public Product() {}
}
