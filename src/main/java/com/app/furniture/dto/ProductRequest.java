package com.app.furniture.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotEmpty(message = "Product Name is mandatory")
    @NotBlank(message = "Product Name is mandatory")
    private String name;
    @NotEmpty(message = "Product category Name is mandatory")
    @NotBlank(message = "Product category Name is mandatory")
    private String category;
    @NotEmpty(message = "Product description Name is mandatory")
    @NotBlank(message = "Product description Name is mandatory")
    private String description;
    @NotNull(message = "Product price is mandatory")
    private Double price;
    @NotNull(message = "Product quantity is mandatory")
    private Integer stockQuantity;

}
