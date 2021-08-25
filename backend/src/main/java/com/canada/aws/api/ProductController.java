package com.canada.aws.api;

import com.canada.aws.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/products")
public interface ProductController{
    @PostMapping()
    ResponseEntity<?> createANewProduct(@ModelAttribute  ProductDto productDto);

    @GetMapping()
    ResponseEntity<?> getAllProductsByCategoryIdAndKeyWord(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam String keyword,
            @RequestParam Integer pageIdx,
            @RequestParam Integer perPage,
            @RequestParam(required = false) String brandIdStr,
            @RequestParam(required = false) Integer priceStart,
            @RequestParam(required = false) Integer priceEnd,
            @RequestParam(required = false) String sortType);

    @GetMapping("/{productId}")
    ResponseEntity<?> getProductByProductId(
            @PathVariable("productId") Integer productId,
            @RequestParam(required = false) String userId);

    @GetMapping("/history")
    ResponseEntity<?> getBrowsingHistoryProducts(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) Integer categoryId
    );
}
