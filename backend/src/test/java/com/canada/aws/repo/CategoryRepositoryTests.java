package com.canada.aws.repo;

import com.canada.aws.model.Category;
import com.canada.aws.utils.EntityUtils;
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

    @Autowired
    EntityUtils entityUtils;

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
    public void testGetAllSubCategoriesFromARoot(){
        String root = "5";
        List<Category>subs = categoryRepository.findSubCategoriesFromARoot(root);
        System.out.println(subs.get(0).getName());
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

    @Test
    public void testListAllBrandsByCategoryId(){
        Category randomCategory = entityUtils.generateRandomEntity(categoryRepository, categoryRepository.findAll().get(0).getId());
        List<Integer>brandIds = categoryRepository.findAllBrandIdsByCategoryId(randomCategory.getId());
        assertThat(brandIds.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testFindRootCategoryForSearch(){
        List<Category>rootCategories = categoryRepository.findRootCategoriesForSearch();

        assertThat(rootCategories.size()).isGreaterThan(0);
    }
}
