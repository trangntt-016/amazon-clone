package com.canada.aws.repo;

import com.canada.aws.model.Brand;
import com.canada.aws.model.Category;
import com.canada.aws.model.Product;
import com.canada.aws.repo.custom.CustomProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, CustomProductRepository{
    @Query("SELECT p FROM Product p WHERE p.brand.id =:brandId")
    List<Product> findAllByBrandId(Integer brandId);

    @Query("SELECT p FROM Product p WHERE p.category.id =:categoryId")
    List<Product> findAllByCategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    List<Product> findAllByProductName(String keyword);


}
