package com.fran.inventory_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierRequest {

    private Long id;

    @NotBlank(message = "the name is mandatory")
    @Size(max = 100, message = "the name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "the address is mandatory")
    @Size(max = 200, message = "the address must not exceed 200 characters")
    private String address;

    @NotBlank(message = "the email is mandatory")
    @Email(message = "the email is not valid")
    @Size(max = 100, message = "the email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "the phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "the phone number is not valid")
    private String phone;

    @Size(max = 15, message = "the tax identification must not exceed 15 characters")
    private String taxIdentification;
}
