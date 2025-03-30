package com.app.furniture.service;

import com.app.furniture.dto.OrderItemResponse;
import com.app.furniture.dto.OrderResponse;
import com.app.furniture.dto.PageResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface OrderService {

    PageResponse<OrderResponse> getAllOrders(Integer page, Integer size, Authentication connectedUser) throws BadRequestException;

    PageResponse<OrderItemResponse> getItemsByOrderId(Integer orderId, Integer page, Integer size, Authentication connectedUser) throws BadRequestException;

    void updateOrderState();

}
