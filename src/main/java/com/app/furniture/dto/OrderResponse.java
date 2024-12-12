package com.app.furniture.dto;

import java.time.LocalDate;

public record OrderResponse(
        Integer id,
        LocalDate orderedAt,
        LocalDate deliveredAt,
        String state,
        Double totalPrice
) {
}
