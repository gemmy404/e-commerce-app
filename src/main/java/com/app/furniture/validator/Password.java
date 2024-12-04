package com.app.furniture.validator;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "Password is incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}