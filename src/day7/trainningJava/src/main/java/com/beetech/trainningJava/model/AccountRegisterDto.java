package com.beetech.trainningJava.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;


/**
 * DTO for {@link com.beetech.trainningJava.entity.AccountEntity}
 */
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AccountRegisterDto implements Serializable {
    @NotBlank(message = "Password is mandatory")
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters long")
    String password;

    @NotBlank(message = "Matching password must not be blank")
    @NotNull(message = "Matching password must not be null")
    String matchingPassword;

    @NotBlank(message = "Name must not be blank")
    @NotEmpty(message = "Name must not be empty")
    @Size(max = 50, message = "Name's length cannot exceed 50 characters")
    String name;

    @NotNull(message = "Email is mandatory")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email's length cannot exceed 100 characters")
    String email;

    @NotBlank(message = "Username must not be blank")
    @NotEmpty(message = "Username must not be empty")
    @Size(max = 50, message = "Username's length cannot exceed 50 characters")
    String username;
}