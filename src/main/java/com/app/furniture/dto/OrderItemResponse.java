package com.app.furniture.dto;

public record OrderItemResponse(
        String productName,
        byte[] productImage,
        Integer quantity,
        Double price
) {
}
