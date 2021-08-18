package com.canada.aws.service;

import com.canada.aws.dto.ProductDto;
import com.canada.aws.model.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ProductService {
    Product createAProduct(ProductDto productDto) throws IOException;
}
