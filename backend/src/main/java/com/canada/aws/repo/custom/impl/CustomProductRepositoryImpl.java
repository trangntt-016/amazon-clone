package com.canada.aws.repo.custom.impl;

import com.canada.aws.model.Category;
import com.canada.aws.model.Product;
import com.canada.aws.repo.custom.CustomProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final EntityManager entityManager;

    @Autowired
    public CustomProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Product> findAllByCategoryIdsKeyword(List<Integer> ids, String keyword) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ids.size(); i++){
            sb.append("SELECT * FROM products WHERE category_id = ").append(ids.get(i)).append(" ")
                    .append(" AND (LOWER(name) LIKE '%").append(keyword).append("%' ")
                    .append("OR full_description LIKE '%").append(keyword).append("%')");
            if(i<ids.size()-1){
                sb.append(" UNION ");
            }
        }
        Query q = entityManager.createNativeQuery(sb.toString(),Product.class);

        return  q.getResultList();
    }

    @Override
    public List<Product> findAllInspiredProductsByProductList(List<Product>browsingProducts){
        String categoriesString = browsingProducts.stream().map(Product::getCategory).map(Category::getId).collect(Collectors.toList()).toString().replace("[","").replace("]","");
        String productsString = browsingProducts.stream().map(Product::getId).collect(Collectors.toList()).toString().replace("[","").replace("]","");;

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * from products WHERE category_id in (").append(categoriesString).append(") ")
                .append("AND id NOT IN (").append(productsString).append(")");

        Query q = entityManager.createNativeQuery(sb.toString(),Product.class);

        return  q.getResultList();
    }


}
