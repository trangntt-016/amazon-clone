package com.canada.aws.repo;

import com.canada.aws.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProductByIdKeyword(){
        List<Product> productList = productRepository.findAllByCategoryIdsKeyword(List.of(5),"style");

        assertThat(productList.size()).isGreaterThanOrEqualTo(0);
    }
}
