package com.canada.aws.repo.custom;

import com.canada.aws.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {
    List<Product> findAllByCategoryIdsKeyword(List<Integer> ids, String keyword);

    List<Product> findAllInspiredProductsByProductList(List<Product>browsingProducts);
}
