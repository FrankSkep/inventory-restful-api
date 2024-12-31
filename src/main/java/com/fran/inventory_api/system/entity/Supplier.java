package com.fran.inventory_api.system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
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

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "tax_identification", nullable = false)
    private String taxIdentification;

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
