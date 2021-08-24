package com.canada.aws.service.impl;

import com.canada.aws.model.Brand;
import com.canada.aws.model.Category;
import com.canada.aws.repo.BrandRepository;
import com.canada.aws.repo.CategoryRepository;
import com.canada.aws.service.BrandService;
import com.canada.aws.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandServiceImpl brandService;


    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategories();
    }

    @Override
    public List<Category> getRootCategoriesForSearch(){
        return categoryRepository.findRootCategoriesForSearch();
    }

    @Override
    public List<Brand> getAllBrandsByCategoryId(Integer categoryId) {
        List<Integer>brandIds = categoryRepository.findAllBrandIdsByCategoryId(categoryId);

        return brandService.getBrandsByBrandIds(brandIds);
    }

}
