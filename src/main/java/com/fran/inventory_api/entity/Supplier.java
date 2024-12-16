package com.fran.inventory_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "tax_identification", nullable = false)
    private String taxIdentification;

    // Relación con productos
    @OneToMany(mappedBy = "supplier")
    @JsonManagedReference
    private Set<Product> products;

    public Supplier(String name, String address, String email, String phone, String taxIdentification) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.taxIdentification = taxIdentification;
    }

    public Supplier(Long id, String name, String address, String email, String phone, String taxIdentification) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.taxIdentification = taxIdentification;
    }
}
