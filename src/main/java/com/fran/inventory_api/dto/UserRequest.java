package com.fran.inventory_api.dto;

import com.fran.inventory_api.auth.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Id is mandatory")
    Long id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "First name is mandatory")
    private String firstname;

    @NotBlank(message = "Last name is mandatory")
    private String lastname;

    @NotBlank(message = "Country is mandatory")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Country must contain only letters and spaces")
    private String country;
}
