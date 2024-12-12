package com.app.furniture.repository;

import com.app.furniture.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {

    Page<OrderItem> findByOrder_Id(Integer orderId, Pageable pageable);

}
