package com.canada.aws.service.impl;

import com.canada.aws.model.Category;
import com.canada.aws.repo.CategoryRepository;
import com.canada.aws.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategories();
    }
}
