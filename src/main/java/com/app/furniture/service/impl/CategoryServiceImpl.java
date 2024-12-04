package com.app.furniture.service.impl;

import com.app.furniture.dto.CategoryResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.entity.Category;
import com.app.furniture.mapper.CategoryMapper;
import com.app.furniture.repository.CategoryRepo;
import com.app.furniture.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public PageResponse<CategoryResponse> findAllCategories(Integer page, Integer size) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryRepo.findAll(pageable);
        return categoryMapper.toCategoryResponsePage(categories);
    }

}
