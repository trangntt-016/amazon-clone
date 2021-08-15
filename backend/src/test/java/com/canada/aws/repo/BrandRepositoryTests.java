package com.canada.aws.repo;

import com.canada.aws.model.Brand;
import com.canada.aws.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.EntityUtils;

@SpringBootTest
public class BrandRepositoryTests {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityUtils entityUtils;

    @Test
    public void testCreateNewBrand(){
        Brand newBrand = Brand.builder()
                .logo("png").name("Acer").build();

        brandRepository.save(newBrand);
    }
}
