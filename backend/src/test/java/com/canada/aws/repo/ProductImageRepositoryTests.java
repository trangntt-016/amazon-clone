package com.canada.aws.repo;

import com.canada.aws.model.Product;
import com.canada.aws.model.ProductImage;
import com.canada.aws.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductImageRepositoryTests {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityUtils utils;

    @Test
    public void getExtraImagesFromProductId(){
        Product randomProduct = utils.generateRandomEntity(productRepository, productRepository.findAll().get(0).getId());

        List<ProductImage>productImages = productImageRepository.findProductImagesByProductId(randomProduct.getId());

        assertThat(productImages.size()).isGreaterThanOrEqualTo(0);
    }

}
