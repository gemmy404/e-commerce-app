package com.app.furniture.dto;

import com.app.furniture.validator.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegistrationRequest(

        @NotEmpty(message = "Firstname is mandatory")
        @NotBlank(message = "Firstname is mandatory")
        String firstName,
        @NotEmpty(message = "Lastname is mandatory")
        @NotBlank(message = "Lastname is mandatory")
        String lastName,
        @Email(message = "Email is not formatted")
        @NotEmpty(message = "Email is mandatory")
        @NotBlank(message = "Email is mandatory")
        String email,
        @Password(message = "Please choose a strong password. Try a combination of letters, numbers and symbols.")
        String password,
        @Password(message = "Please choose a strong password. Try a combination of letters, numbers and symbols.")
        String confirmPassword,
        @NotEmpty(message = "phone is mandatory")
        @NotBlank(message = "phone is mandatory")
        String phone

) {
}
