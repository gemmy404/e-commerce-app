package com.app.furniture.service.impl;

import com.app.furniture.dto.PageResponse;
import com.app.furniture.dto.ProductResponse;
import com.app.furniture.entity.Product;
import com.app.furniture.entity.User;
import com.app.furniture.entity.WishList;
import com.app.furniture.mapper.ProductMapper;
import com.app.furniture.repository.ProductRepo;
import com.app.furniture.repository.WishListRepo;
import com.app.furniture.repository.specification.WishListSpecification;
import com.app.furniture.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.app.furniture.repository.specification.ProductSpecification.hasCategoryName;
import static com.app.furniture.repository.specification.ProductSpecification.searchKeyword;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final WishListRepo wishListRepo;

    @Override
    public ProductResponse findById(Integer productId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Optional<WishList> favoriteProduct = wishListRepo.findOne(WishListSpecification
                .findByUserIdAndProductId(user.getId(), productId));
        ProductResponse response = productRepo.findById(productId)
                .map(productMapper::toProductResponse).orElseThrow(() -> new
                        EntityNotFoundException("Product not found"));
        if (favoriteProduct.isPresent()) {
            response.setFavorite(true);
        }
        return response;
    }

    @Override
    public PageResponse<ProductResponse> findAll(Integer page, Integer size, Authentication connectedUser) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(DESC, "createdDate"));
        Page<Product> products = productRepo.findAll(pageable);
        List<Integer> favoriteProductIds = wishListRepo.findAll(WishListSpecification
                        .findFavoriteProductIdsByUserId(user.getId())).stream()
                .map(wishList -> wishList.getProduct().getId())
                .toList();
        products.forEach(product -> product.setIsFavorite(favoriteProductIds.contains(product.getId())));
        return productMapper.toPageResponse(products);
    }

    @Override
    public PageResponse<ProductResponse> searchProduct(String keyword, Integer page, Integer size, Authentication connectedUser) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(DESC, "createdDate"));
        Page<Product> products = productRepo.findAll(searchKeyword(keyword), pageable);
        List<Integer> favoriteProductIds = wishListRepo.findAll(WishListSpecification
                        .findFavoriteProductIdsByUserId(user.getId())).stream()
                .map(wishList -> wishList.getProduct().getId())
                .toList();
        products.forEach(product -> product.setIsFavorite(favoriteProductIds.contains(product.getId())));
        return productMapper.toPageResponse(products);
    }

    @Override
    public PageResponse<ProductResponse> filterProductsByCategory(String categoryName, Integer page, Integer size, Authentication connectedUser) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(DESC, "createdDate"));
        Page<Product> products = productRepo.findAll(hasCategoryName(categoryName), pageable);
        List<Integer> favoriteProductIds = wishListRepo.findAll(WishListSpecification
                        .findFavoriteProductIdsByUserId(user.getId())).stream()
                .map(wishList -> wishList.getProduct().getId())
                .toList();
        products.forEach(product -> product.setIsFavorite(favoriteProductIds.contains(product.getId())));
        return productMapper.toPageResponse(products);
    }

    @Override
    public void addProductToWishList(Integer productId, Authentication connectedUser) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new
                EntityNotFoundException("Product with id: " + productId + " not found."));
        User user = (User) connectedUser.getPrincipal();
        boolean isExist = wishListRepo.findOne(WishListSpecification.findByUserIdAndProductId(user.getId(), productId)).isPresent();
        if (isExist) {
            throw new DuplicateKeyException("Product with id: " + productId + " already exists in the wish list.");
        }
        WishList wishList = WishList.builder()
                .product(product)
                .user(user)
                .build();
        wishListRepo.save(wishList);
    }

    @Override
    public PageResponse<ProductResponse> findAllFavoriteProducts(Integer page, Integer size, Authentication connectedUser) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(DESC, "createdDate"));
        Page<Product> products = wishListRepo.findAll(WishListSpecification.findByUserId(user.getId()), pageable)
                .map(WishList::getProduct);
        products.forEach(product -> product.setIsFavorite(true));
        return productMapper.toPageResponse(products);
    }

    @Override
    public void removeProductFromWishList(Integer productId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Optional<WishList> item = wishListRepo.findOne(WishListSpecification.findByUserIdAndProductId(user.getId(), productId));
        wishListRepo.deleteById(item.map(WishList::getId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found.")));
    }

}
