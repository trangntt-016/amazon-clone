package com.canada.aws.service.impl;

import com.canada.aws.model.Brand;
import com.canada.aws.model.Category;
import com.canada.aws.repo.BrandRepository;
import com.canada.aws.repo.CategoryRepository;
import com.canada.aws.service.BrandService;
import com.canada.aws.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public List<Category> getTreeCategoriesByCategoryId(Integer categoryId) {
        Optional<Category>category = categoryRepository.findById(categoryId);

        if(category.isPresent() && category.get().getAllParentIDs()!=null){
            int[] ids = Arrays.stream(category.get().getAllParentIDs().split(",")).mapToInt(Integer::parseInt).toArray();

            List<Category>categories = new ArrayList<>();

            for(Integer i: ids){
                Category found = categoryRepository.findById(i).get();
                categories.add(found);
            }

            return categories;
        }
        return null;
    }

}
