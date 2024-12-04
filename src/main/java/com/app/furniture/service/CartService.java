package com.app.furniture.service;

import com.app.furniture.dto.CartItemResponse;
import com.app.furniture.dto.PageResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface CartService {

    void addProductToCart(Integer productId, Integer quantity, Authentication connectedUser) throws BadRequestException;

    PageResponse<CartItemResponse> findAllProductsInCart(Integer page, Integer size, Authentication connectedUser) throws BadRequestException;

    Integer updateQuantity(Integer itemId, Integer quantity) throws BadRequestException;

    void removeProductFromCart(Integer cartItemId, Authentication connectedUser);

    void removeAllProductsFromCart(Authentication connectedUser);

    //    void checkoutProducts(Authentication connectedUser);

}
