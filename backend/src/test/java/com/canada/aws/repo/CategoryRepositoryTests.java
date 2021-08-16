package com.canada.aws.repo;

import com.canada.aws.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CategoryRepositoryTests {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory(){
        Category category = Category.builder().name("Computer").alias("Computer").build();
        Category saved = categoryRepository.save(category);

        assertThat(saved.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory(){
        Category parent = categoryRepository.findById(1).get();

        Category sub = Category.builder().parent(parent).name("RAM").alias("RAM").build();

        categoryRepository.save(sub);
    }

    @Test
    public void testCreateSubSubCategory(){
        Category parent = categoryRepository.findById(4).get();

        Category sub = Category.builder().parent(parent).name("Electronic Wire").alias("Electronic Wire").build();

        categoryRepository.save(sub);
    }

    @Test
    public void testGetAllChildrenFromSubCategory(){
        Category parent = categoryRepository.findById(2).get();
        Set<Category> childrenCategory = parent.getChildren();
        for(Category category: childrenCategory){
            System.out.println(category.getName());
        }
    }

    @Test
    public void testGetHierarchicalCategories(){
        Iterable<Category> categories = categoryRepository.findAll();

        for(Category category:categories) {
            // root category
            if(category.getParent()==null) {
                System.out.println(category.getName());

                Set<Category>children = category.getChildren();

                for(Category sub: children){
                    System.out.println("--"+sub.getName());
                }
            }

        }
    }

    @Test
    public void testListRootCategories() {
        List<Category> roots = categoryRepository.findRootCategories();

        assertThat(roots.size()).isGreaterThan(0);
    }
}
