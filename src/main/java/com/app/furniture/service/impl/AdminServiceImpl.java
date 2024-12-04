package com.app.furniture.service.impl;

import com.app.furniture.dto.ProductRequest;
import com.app.furniture.entity.Category;
import com.app.furniture.entity.Product;
import com.app.furniture.entity.User;
import com.app.furniture.mapper.ProductMapper;
import com.app.furniture.repository.CartItemRepo;
import com.app.furniture.repository.CategoryRepo;
import com.app.furniture.repository.ProductRepo;
import com.app.furniture.repository.WishListRepo;
import com.app.furniture.repository.specification.CartItemSpecification;
import com.app.furniture.repository.specification.WishListSpecification;
import com.app.furniture.service.AdminService;
import com.app.furniture.util.FileStorageUtil;
import com.app.furniture.util.UpdateUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;
    private final FileStorageUtil fileStorageUtil;
    private final CartItemRepo cartItemRepo;
    private final WishListRepo wishListRepo;

    @Override
    public Integer addProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        Category category = categoryRepo.findByName(request.getCategory()).orElse(new Category(request.getCategory()));
        product.setCategory(category);
        product.setIsAvailable(true);
        product.setIsFavorite(false);
        return productRepo.save(product).getId();
    }

    @Override
    public Integer updateProduct(Integer productId, ProductRequest request) throws BadRequestException {
        if (productId == null || productId <= 0) {
            throw new BadRequestException("Invalid product id");
        }
        Product existingProduct = productRepo.findById(productId).orElseThrow(() -> new
                EntityNotFoundException("Product with id " + productId + " not found"));
        Product product = productMapper.toProduct(request);
        Category category = categoryRepo.findByName(existingProduct.getCategory().getName()).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));
        product.setCategory(category);
        Product product1 = UpdateUtil.updateValues(product, existingProduct);
        return productRepo.save(product1).getId();
    }

    @Override
    public void deleteProduct(int productId) {
        if (productRepo.findById(productId).isEmpty()) {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        }
        cartItemRepo.deleteAll(cartItemRepo.findAll(CartItemSpecification.findByProductId(productId)));
        wishListRepo.deleteAll(wishListRepo.findAll(WishListSpecification.findByProductId(productId)));
        productRepo.deleteById(productId);
    }

    @Override
    public void uploadProductImage(Integer productId, MultipartFile file, Authentication connectedUser) throws IOException {
        User user = (User) connectedUser.getPrincipal();
        String productImage = fileStorageUtil.saveFile(file, "products", user.getId());
        Product product = productRepo.findById(productId).orElseThrow(() -> new
                EntityNotFoundException("Product with id " + productId + " not found"));
        product.setImage(productImage);
        productRepo.save(product);
    }

}
