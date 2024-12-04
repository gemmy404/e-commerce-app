package com.app.furniture.repository;

import com.app.furniture.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WishListRepo extends JpaRepository<WishList, Integer>,
        JpaSpecificationExecutor<WishList> {
}
