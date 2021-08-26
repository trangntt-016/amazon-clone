package com.canada.aws.api;

import com.canada.aws.dto.CartItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/carts")
public interface CartItemController {
    @PostMapping("/add")
    ResponseEntity<?> addProductToCart(@RequestBody CartItemDto cartItemDto);

}

