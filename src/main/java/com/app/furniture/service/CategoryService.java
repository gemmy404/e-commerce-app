package com.app.furniture.service;

import com.app.furniture.dto.CategoryResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.dto.ProductResponse;
import com.app.furniture.entity.Category;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> findByName(String name);

    PageResponse<CategoryResponse> findAllCategories(Integer page, Integer size) throws BadRequestException;

//    PageResponse<ProductResponse> filterProductsByCategory(String categoryName, Integer page, Integer size, Authentication connectedUser);

}
