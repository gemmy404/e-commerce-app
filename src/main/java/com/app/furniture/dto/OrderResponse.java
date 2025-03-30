package com.app.furniture.dto;

import com.app.furniture.enums.OrderState;

import java.time.LocalDateTime;

public record OrderResponse(
        Integer id,
        LocalDateTime orderedAt,
        LocalDateTime deliveredAt,
        OrderState state,
        Double totalPrice
) {
}
