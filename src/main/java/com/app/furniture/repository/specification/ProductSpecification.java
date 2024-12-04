package com.app.furniture.repository.specification;

import com.app.furniture.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("name"), categoryName);
    }

    public static Specification<Product> searchKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String searchKeyword = "%" + keyword.toLowerCase() + "%";
            Predicate namePredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("name")), searchKeyword);
            Predicate descriptionPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("description")), searchKeyword);
            return criteriaBuilder.or(namePredicate, descriptionPredicate);
        };
    }

}
