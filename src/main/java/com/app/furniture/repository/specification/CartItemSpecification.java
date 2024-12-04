package com.app.furniture.repository.specification;

import com.app.furniture.entity.CartItem;
import org.springframework.data.jpa.domain.Specification;

public class CartItemSpecification {

    public static Specification<CartItem> findByUserId(Integer userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<CartItem> findByProductId(Integer productId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("product").get("id"), productId);
    }

}
