package com.app.furniture.controller;

import com.app.furniture.dto.ProductRequest;
import com.app.furniture.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
@Tag(name = "AdminDashboard")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Integer> addProduct(@Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(adminService.addProduct(request), HttpStatus.CREATED);
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<Integer> updateProduct(@PathVariable("product-id") Integer productId,
                                                 @RequestBody ProductRequest request) throws BadRequestException {
        return new ResponseEntity<>(adminService.updateProduct(productId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("product-id") Integer productId) {
        adminService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{product-id}/image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadProductImage(@PathVariable("product-id") Integer productId,
                                                @RequestPart("file") MultipartFile file,
                                                Authentication connectedUser) throws IOException {
        adminService.uploadProductImage(productId, file, connectedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
