package com.canada.aws.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/categories")
public interface CategoryController {
    @GetMapping("/root")
    ResponseEntity<?> getRootCategories();

    @GetMapping("/{categoryId}/brands")
    ResponseEntity<?> getAllBrandsByCategoryId(@PathVariable Integer categoryId);
}
