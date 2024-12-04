package com.app.furniture.mapper;

import com.app.furniture.dto.CategoryResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.entity.Category;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(Category category);

    PageResponse<CategoryResponse> toCategoryResponsePage(Page<Category> categoryPage);

}
