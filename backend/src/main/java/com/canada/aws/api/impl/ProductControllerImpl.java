package com.canada.aws.api.impl;

import com.canada.aws.api.ProductController;
import com.canada.aws.dto.ProductDetailsDto;
import com.canada.aws.dto.ProductDto;
import com.canada.aws.dto.ProductSearchDto;
import com.canada.aws.dto.ProductSearchResultDto;
import com.canada.aws.model.Product;
import com.canada.aws.service.impl.ProductServiceImpl;
import com.canada.aws.service.impl.UserEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/products")
public class ProductControllerImpl implements ProductController {
    @Autowired
    ProductServiceImpl productService;

    @Autowired
    UserEntityServiceImpl userService;

    @Override
    @PostMapping()
    public ResponseEntity<?> createANewProduct(ProductDto productDto){
        try{
            Product product = productService.createAProduct(productDto);
            return ResponseEntity.ok(product);
        }
        catch(Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAllProductsByCategoryIdAndKeyWord(
            Integer categoryId,
            String keyword,
            Integer pageIdx,
            Integer perPage,
            String brandIdStr,
            Integer priceStart,
            Integer priceEnd,
            String sortType) {
        // find all categories and brands
        List<Product> productsByCategoryKeyword = new ArrayList<>();
        List<Product>productsByFilter = null;
        if(categoryId == null){
            productsByCategoryKeyword = productService.getAllProductsByKeyword(keyword);
        }
        //find with category id and keyword
        else{
            productsByCategoryKeyword = productService.getAllProductsByCategoryIdAndKeyword(categoryId, keyword);

        }

        if(brandIdStr!=null){
            List<Integer> brandIds = Arrays.stream(brandIdStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            productsByFilter = productService.filterByBrands(productsByCategoryKeyword, brandIds);
        }
        if(priceStart!=null && priceEnd != null){
            productsByFilter = (productsByFilter == null) ? productsByCategoryKeyword : productsByFilter;
            productsByFilter = productService.filterByPrice(productsByFilter, priceStart, priceEnd);
        }
        if(sortType!=null && sortType != ""){
            productsByFilter = (productsByFilter == null) ? productsByCategoryKeyword : productsByFilter;
            productsByFilter = productService.sortProducts(productsByFilter, sortType);
        }
        ProductSearchResultDto resultDto = productService.getSearchResult(productsByCategoryKeyword,productsByFilter, pageIdx, perPage, keyword);

        return ResponseEntity.ok(resultDto);
    }

    @Override
    public ResponseEntity<?> getProductByProductId(Integer productId, String userId) {
        ProductDetailsDto dto = productService.getProductByProductId(productId);
        if(dto != null){
            userService.updateBrowsingHistory(userId, dto.getId());
        }
        return ResponseEntity.ok(productService.getProductByProductId(productId));
    }

    @Override
    public ResponseEntity<?> getBrowsingHistoryProducts(String userId, Integer categoryId) {
        return ResponseEntity.ok(productService.getBrowsingHistoryProducts(userId, categoryId));
    }

}
