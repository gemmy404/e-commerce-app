package com.app.furniture.repository;

import com.app.furniture.entity.Order;
import com.app.furniture.enums.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    Page<Order> findAllByUser_Id(Integer userId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.orderedAt <= :date AND o.state = :state")
    List<Order> findAllPendingOrders(@Param("date") LocalDateTime date, @Param("state") OrderState state);

}
