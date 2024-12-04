package com.app.furniture.service;

import com.app.furniture.dto.PageResponse;
import com.app.furniture.dto.ProductResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface ProductService {

    ProductResponse findById(Integer id, Authentication connectedUser);

    PageResponse<ProductResponse> findAll(Integer page, Integer size, Authentication connectedUser) throws BadRequestException;

    PageResponse<ProductResponse> searchProduct(String keyword, Integer page, Integer size, Authentication connectedUser) throws BadRequestException;

    PageResponse<ProductResponse> filterProductsByCategory(String categoryName, Integer page, Integer size, Authentication connectedUser) throws BadRequestException;

    void addProductToWishList(Integer productId, Authentication connectedUser);

    PageResponse<ProductResponse> findAllFavoriteProducts(Integer page, Integer size, Authentication connectedUser) throws BadRequestException;

    void removeProductFromWishList(Integer productId, Authentication connectedUser);

}
