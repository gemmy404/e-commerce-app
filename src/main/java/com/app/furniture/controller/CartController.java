package com.app.furniture.controller;

import com.app.furniture.dto.CartItemResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestParam("productId") Integer productId,
                                       @RequestParam(name = "quantity", defaultValue = "1", required = false) Integer quantity,
                                       Authentication connectedUser) throws BadRequestException {
        cartService.addProductToCart(productId, quantity, connectedUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CartItemResponse>> getProductsInCart(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
            Authentication connectedUser) throws BadRequestException {
        return ResponseEntity.ok(cartService.findAllProductsInCart(page, size, connectedUser));
    }

    @PatchMapping
    public ResponseEntity<Integer> updateQuantity(@RequestParam Integer itemId, @RequestParam Integer quantity) throws BadRequestException {
        return ResponseEntity.ok(cartService.updateQuantity(itemId, quantity));
    }
    
    @DeleteMapping("/{cart-item-id}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable("cart-item-id") Integer cartItemId,
                                                   Authentication connectedUser) {
        cartService.removeProductFromCart(cartItemId, connectedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> removeAllProductsFromCart(Authentication connectedUser) {
        cartService.removeAllProductsFromCart(connectedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

}
