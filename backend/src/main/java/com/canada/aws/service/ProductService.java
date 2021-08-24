package com.canada.aws.service;

import com.canada.aws.dto.ProductDto;
import com.canada.aws.dto.ProductSearchDto;
import com.canada.aws.dto.ProductSearchResultDto;
import com.canada.aws.model.Product;
import com.canada.aws.service.impl.ProductServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    Product createAProduct(ProductDto productDto) throws IOException;
    List<Product> getAllProductsByKeyword(String keyword);

    List<Product> getAllProductsByCategoryIdAndKeyword(Integer categoryId, String keyword);

    List<Product> filterByBrands(List<Product> products, List<Integer>brandIds);

    List<Product> filterByPrice(List<Product> products, Integer priceStart, Integer priceEnd);

    List<Product> sortProducts(List<Product> products, String sortType);

    ProductSearchResultDto getSearchResult(List<Product> productsByCategoryKeyword,List<Product> productByFilter, Integer pageIdx, Integer perPage, String keyword);

}
