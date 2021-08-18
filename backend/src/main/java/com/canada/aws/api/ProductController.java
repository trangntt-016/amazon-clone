package com.canada.aws.api;

import com.canada.aws.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RequestMapping("/api/products")
public interface ProductController {
    @PostMapping()
    ResponseEntity<?> createANewProduct(@ModelAttribute  ProductDto productDto);
}
