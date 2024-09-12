package com.fran.Sistema_Inventario.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no debe exceder los 200 caracteres")
    private String direccion;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico no debe exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "El número de teléfono no es válido")
    private String telefono;

    @Size(max = 15, message = "El número de identificación fiscal no debe exceder los 15 caracteres")
    private String identificacionFiscal;

    // Relación con productos
    @OneToMany(mappedBy = "proveedor")
    @JsonManagedReference
    private Set<Producto> productos;

    public Proveedor() {
    }

    public Proveedor(String nombre, String direccion, String email, String telefono, String identificacionFiscal) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.identificacionFiscal = identificacionFiscal;
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

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

}
