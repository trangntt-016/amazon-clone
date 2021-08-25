package com.canada.aws.repo;

import com.canada.aws.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    @Query("SELECT p FROM ProductImage p WHERE p.product.id =:productId")
    List<ProductImage> findProductImagesByProductId(Integer productId);
}
