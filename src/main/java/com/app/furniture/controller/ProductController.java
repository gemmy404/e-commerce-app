package com.app.furniture.controller;

import com.app.furniture.dto.PageResponse;
import com.app.furniture.dto.ProductResponse;
import com.app.furniture.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getAllProduct(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "8", required = false) Integer size,
            Authentication connectedUser) throws BadRequestException {
        return ResponseEntity.ok(productService.findAll(page, size, connectedUser));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("product-id") Integer productId,
                                                      Authentication connectedUser) {
        return ResponseEntity.ok(productService.findById(productId, connectedUser));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductResponse>> searchProduct(
            @RequestParam("keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "8", required = false) Integer size,
            Authentication connectedUser) throws BadRequestException {
        return ResponseEntity.ok(productService.searchProduct(keyword, page, size, connectedUser));
    }

    @GetMapping("/category")
    public ResponseEntity<PageResponse<ProductResponse>> findAllProductsByCategory(
            @RequestParam("category") String categoryName,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "8", required = false) Integer size,
            Authentication connectedUser) throws BadRequestException {
        return ResponseEntity.ok(productService.filterProductsByCategory(categoryName, page, size, connectedUser));
    }

    @PostMapping("/add-to-wish-list/{product-id}")
    public ResponseEntity<?> addToWishList(@PathVariable("product-id") Integer productId,
                                           Authentication connectedUser) {
        productService.addProductToWishList(productId, connectedUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/wish-list")
    public ResponseEntity<?> getFavoriteProducts(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
            Authentication connectedUser) throws BadRequestException {
        return ResponseEntity.ok(productService.findAllFavoriteProducts(page, size, connectedUser));
    }

    @DeleteMapping("/wish-list/{product-id}")
    public ResponseEntity<?> removeFavoriteProducts(@PathVariable("product-id") Integer productId,
                                                    Authentication connectedUser) {
        productService.removeProductFromWishList(productId, connectedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
