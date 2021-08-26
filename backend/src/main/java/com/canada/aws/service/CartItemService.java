package com.canada.aws.service;


import com.canada.aws.dto.CartItemDto;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    CartItemDto addProductToCart(String userId, Integer productId, Integer quantity);
}
