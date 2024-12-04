package com.app.furniture.service;

import com.app.furniture.dto.ProductRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdminService {

    Integer addProduct(ProductRequest request);

    Integer updateProduct(Integer productId, ProductRequest request) throws BadRequestException;

    void deleteProduct(int productId);

    void uploadProductImage(Integer productId, MultipartFile file, Authentication connectedUser) throws IOException;

}
