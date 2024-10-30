package com.fran.Sistema_Inventario.DTO.App.Proveedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProveedorRequest {

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
}
