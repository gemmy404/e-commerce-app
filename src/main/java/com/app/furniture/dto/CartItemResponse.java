package com.app.furniture.dto;

public record CartItemResponse(

        Integer itemId,
        String productName,
        String category,
        Double price,
        Integer quantity,
        byte[] image

) {
}
