package com.app.furniture.mapper;

import com.app.furniture.dto.CartItemResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.entity.CartItem;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import static com.app.furniture.util.FileStorageUtil.readFileToByteArray;

@Mapper
public interface CartItemMapper {

    default CartItemResponse toCartItemResponse(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getCategory().getName(),
                cartItem.getPrice(),
                cartItem.getQuantity(),
                readFileToByteArray(cartItem.getProduct().getImage())
        );
    }

    PageResponse<CartItemResponse> toPageResponse(Page<CartItem> products);

}
