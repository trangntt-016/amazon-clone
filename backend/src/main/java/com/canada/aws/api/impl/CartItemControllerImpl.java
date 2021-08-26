package com.canada.aws.api.impl;

import com.canada.aws.api.CartItemController;
import com.canada.aws.api.exception.BadRequestException;
import com.canada.aws.dto.CartItemDto;
import com.canada.aws.model.CartItem;
import com.canada.aws.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartItemControllerImpl implements CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Override
    public ResponseEntity<?> addProductToCart(@RequestBody CartItemDto cartItemDto) {
        try{
            CartItemDto cartItem = cartItemService.addProductToCart(cartItemDto.getUserId(), cartItemDto.getProductId(), cartItemDto.getQuantity());
            return ResponseEntity.ok(cartItem);
        }
        catch (BadRequestException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body("Unable to add product to cart.");
        }
    }
}
