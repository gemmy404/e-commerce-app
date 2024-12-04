package com.app.furniture.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;
    private String name;
    private String category;
    private String description;
    private Double price;
//    private Integer stockQuantity;
    private boolean isAvailable;
    private boolean isFavorite;
    private byte[] image;

}
