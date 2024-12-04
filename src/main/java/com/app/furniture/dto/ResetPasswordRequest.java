package com.app.furniture.dto;

import com.app.furniture.validator.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordRequest(

        @NotEmpty(message = "Token should is entered")
        @NotBlank(message = "Token should is entered")
        String token,
        @Password(message = "Please choose a strong password. Try a combination of letters, numbers and symbols.")
        String newPassword,
        @Password(message = "Please choose a strong password. Try a combination of letters, numbers and symbols.")
        String confirmPassword
) {
}
