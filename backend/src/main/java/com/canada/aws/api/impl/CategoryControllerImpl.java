package com.canada.aws.api.impl;


import com.canada.aws.api.CategoryController;
import com.canada.aws.model.Brand;
import com.canada.aws.model.Category;
import com.canada.aws.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerImpl implements CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    @Override
    public ResponseEntity<?> getRootCategories() {
        List<Category>rootCategories = categoryService.getRootCategories();

        return ResponseEntity.ok(rootCategories);
    }

    @Override
    public ResponseEntity<?> getRootCategoriesForSearch() {
        List<Category> categories = categoryService.getRootCategoriesForSearch();
        return ResponseEntity.ok(categories);
    }

    @Override
    public ResponseEntity<?> getAllBrandsByCategoryId(Integer categoryId) {
        List<Brand>brands = categoryService.getAllBrandsByCategoryId(categoryId);

        return ResponseEntity.ok(brands);
    }
}
