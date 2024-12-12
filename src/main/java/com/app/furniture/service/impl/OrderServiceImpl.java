package com.app.furniture.service.impl;

import com.app.furniture.dto.OrderItemResponse;
import com.app.furniture.dto.OrderResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.entity.Order;
import com.app.furniture.entity.OrderItem;
import com.app.furniture.entity.User;
import com.app.furniture.mapper.OrderMapper;
import com.app.furniture.repository.OrderItemRepo;
import com.app.furniture.repository.OrderRepo;
import com.app.furniture.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderMapper orderMapper;

    @Override
    public PageResponse<OrderResponse> getAllOrders(Integer page, Integer size, Authentication connectedUser) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(DESC, "id"));
        Page<Order> orders = orderRepo.findAllByUser_Id(user.getId(), pageable);
        return orderMapper.toOrderPageResponse(orders);
    }

    @Override
    public PageResponse<OrderItemResponse> getItemsByOrderId(Integer orderId, Integer page, Integer size, Authentication connectedUser) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(DESC, "createdDate"));
        Page<OrderItem> items = orderItemRepo.findByOrder_Id(orderId, pageable);
        return orderMapper.toOrderitemPageResponse(items);
    }


}
