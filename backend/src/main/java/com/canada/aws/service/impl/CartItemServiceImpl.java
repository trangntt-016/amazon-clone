package com.canada.aws.service.impl;

import com.canada.aws.api.exception.BadRequestException;
import com.canada.aws.dto.CartItemDto;
import com.canada.aws.model.CartItem;
import com.canada.aws.model.Product;
import com.canada.aws.model.UserEntity;
import com.canada.aws.repo.CartItemRepository;
import com.canada.aws.repo.ProductRepository;
import com.canada.aws.repo.UserEntityRepository;
import com.canada.aws.service.CartItemService;
import com.canada.aws.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItemDto addProductToCart(String userId, Integer productId, Integer quantity) {
        Optional<UserEntity> user = userRepository.findById(userId);

        Optional<Product> product = productRepository.findById(productId);

        if(user.isPresent() && product.isPresent()){
            CartItem cartItem = CartItem.builder()
                    .product(product.get())
                    .customer(user.get())
                    .quantity(quantity)
                    .build();

            cartItem = cartItemRepository.save(cartItem);

            CartItemDto dto = MapperUtils.mapperObject(cartItem, CartItemDto.class);

            return dto;
        }
        else{
            throw new BadRequestException("Unable to find user with id " + userId + " or product with id " + productId);
        }
    }
}
