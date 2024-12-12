package com.app.furniture.mapper;

import com.app.furniture.dto.OrderItemResponse;
import com.app.furniture.dto.OrderResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.entity.Order;
import com.app.furniture.entity.OrderItem;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import static com.app.furniture.util.FileStorageUtil.readFileToByteArray;

@Mapper
public interface OrderMapper {

//    OrderResponse toOrderResponse(Order order);

    PageResponse<OrderResponse> toOrderPageResponse(Page<Order> orders);

    default OrderItemResponse toOrderItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getProduct().getName(),
                readFileToByteArray(item.getProduct().getImage()),
                item.getQuantity(),
                item.getPrice()
        );
    }

    PageResponse<OrderItemResponse> toOrderitemPageResponse(Page<OrderItem> orderItems);

}
