package com.app.furniture.controller;

import com.app.furniture.dto.OrderItemResponse;
import com.app.furniture.dto.OrderResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> getAllOrders(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
            Authentication connectedUser) throws BadRequestException {
        return ResponseEntity.ok(orderService.getAllOrders(page, size, connectedUser));
    }

    @GetMapping("/item")
    public ResponseEntity<PageResponse<OrderItemResponse>> getOrderItemsByOrderId(
            @RequestParam Integer orderId,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
            Authentication connectedUser) throws BadRequestException {
        return ResponseEntity.ok(orderService.getItemsByOrderId(orderId, page, size, connectedUser));
    }

}
