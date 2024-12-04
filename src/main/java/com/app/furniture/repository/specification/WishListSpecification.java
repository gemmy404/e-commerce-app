package com.app.furniture.repository.specification;

import com.app.furniture.entity.WishList;
import org.springframework.data.jpa.domain.Specification;

public class WishListSpecification {

    public static Specification<WishList> findByUserId(Integer userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<WishList> findByProductId(Integer productId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("product").get("id"), productId);
    }

    public static Specification<WishList> findFavoriteProductIdsByUserId(Integer userId) {
        return (root, query, criteriaBuilder) -> {
            assert query != null;
            query.select(root.get("product").get("id"));
            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    public static Specification<WishList> findByUserIdAndProductId(Integer userId, Integer productId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.equal(root.get("user").get("id"), userId),
                        criteriaBuilder.equal(root.get("product").get("id"), productId));
    }

}
