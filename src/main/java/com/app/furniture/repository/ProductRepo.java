package com.app.furniture.repository;

import com.app.furniture.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer>,
        JpaSpecificationExecutor<Product> {

    Optional<Product> findByName(String name);

}
