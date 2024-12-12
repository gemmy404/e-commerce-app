package com.app.furniture.repository;

import com.app.furniture.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    Page<Order> findAllByUser_Id(Integer userId, Pageable pageable);

}
