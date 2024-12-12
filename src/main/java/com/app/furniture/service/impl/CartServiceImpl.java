package com.app.furniture.service.impl;

import com.app.furniture.dto.CartItemResponse;
import com.app.furniture.dto.PageResponse;
import com.app.furniture.entity.*;
import com.app.furniture.mapper.CartItemMapper;
import com.app.furniture.repository.CartItemRepo;
import com.app.furniture.repository.OrderItemRepo;
import com.app.furniture.repository.OrderRepo;
import com.app.furniture.repository.ProductRepo;
import com.app.furniture.repository.specification.CartItemSpecification;
import com.app.furniture.service.CartService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.app.furniture.repository.specification.CartItemSpecification.findByProductId;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final CartItemMapper cartItemMapper;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;

    @Override
    public void addProductToCart(Integer productId, Integer quantity, Authentication connectedUser) throws BadRequestException {
        User user = (User) connectedUser.getPrincipal();
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Product with id: " + productId + " not found."));
        if (quantity <= 0 || product.getStockQuantity() < quantity) {
            throw new BadRequestException("Insufficient stock for the required quantity of " + product.getName());
        }
        CartItem isAlreadyExist = cartItemRepo.findOne(findByProductId(productId)).orElse(null);
        if (isAlreadyExist != null && isAlreadyExist.getUser().getId().equals(user.getId())) {
            throw new DuplicateKeyException("Product with id: " + productId + " already exists in cart.");
        }
        CartItem cartItem = CartItem.builder()
                .quantity(quantity)
                .price(product.getPrice() * quantity)
                .product(product)
                .user(user).build();
        cartItemRepo.save(cartItem);
    }

    @Override
    public PageResponse<CartItemResponse> findAllProductsInCart(Integer page, Integer size, Authentication connectedUser) throws BadRequestException {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index and size must be greater than 0.");
        }
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(DESC, "createdDate"));
        Page<CartItem> itemPage = cartItemRepo.findAll(CartItemSpecification.findByUserId(user.getId()), pageable);
        return cartItemMapper.toPageResponse(itemPage);
    }

    @Override
    public Integer updateQuantity(Integer itemId, Integer quantity) throws BadRequestException {
        CartItem item = cartItemRepo.findById(itemId).orElseThrow(() -> new
                EntityNotFoundException("Item not found"));
        if (quantity <= 0 || item.getProduct().getStockQuantity() < quantity) {
            throw new BadRequestException("Insufficient stock for the required quantity of " + item.getProduct().getName());
        }
        item.setQuantity(quantity);
        item.setPrice(item.getProduct().getPrice() * quantity);
        cartItemRepo.save(item);
        return quantity;
    }

    @Override
    public void removeProductFromCart(Integer cartItemId, Authentication connectedUser) {
        cartItemRepo.findById(cartItemId).orElseThrow(() -> new
                EntityNotFoundException("Product with id: " + cartItemId + " not found."));
        cartItemRepo.deleteById(cartItemId);
    }

    @Override
    public void removeAllProductsFromCart(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<CartItem> cartItems = cartItemRepo.findAll(CartItemSpecification.findByUserId(user.getId()));
        cartItemRepo.deleteAll(cartItems);
    }

    @Override
    public void checkoutProducts(Authentication connectedUser) throws BadRequestException {
        User user = (User) connectedUser.getPrincipal();
        List<CartItem> items = cartItemRepo.findAll(CartItemSpecification.findByUserId(user.getId()));
        if (items.isEmpty()) {
            throw new BadRequestException("Your cart doesn't contain any products.");
        }
        Order order = Order.builder()
                .orderedAt(LocalDate.now())
                .deliveredAt(LocalDate.now().plusDays(3))
                .state("Ordered")
                .user(user)
                .build();
        Order savedOrder = orderRepo.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        items.forEach(item -> {
            OrderItem orderItem = new OrderItem();
            Product product = item.getProduct();
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setOrder(savedOrder);
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepo.save(product);
            orderItems.add(orderItem);
        });
        Double totalPrice = orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
        savedOrder.setTotalPrice(totalPrice);
        orderRepo.save(savedOrder);
        orderItemRepo.saveAll(orderItems);
        removeAllProductsFromCart(connectedUser);
    }


}
