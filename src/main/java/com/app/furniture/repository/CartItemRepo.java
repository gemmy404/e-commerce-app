package com.app.furniture.repository;

import com.app.furniture.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartItemRepo extends JpaRepository<CartItem, Integer>,
        JpaSpecificationExecutor<CartItem> {

}
