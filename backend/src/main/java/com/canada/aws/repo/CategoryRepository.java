package com.canada.aws.repo;

import com.canada.aws.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findRootCategories();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL  AND length(c.name)<=15 ")
    List<Category> findRootCategoriesForSearch();

    @Query("SELECT c FROM Category c WHERE c.allParentIDs LIKE :root ||',%' OR c.allParentIDs LIKE :root")
    List<Category> findSubCategoriesFromARoot(String root);

    @Query("SELECT c FROM Category c WHERE c.name =:name")
    Optional<Category> findCategoryByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name%")
    List<Category> findCategoriesContainsName(String name);

    @Query("SELECT c FROM Category c WHERE c.parent.id >0")
    List<Category> findLastChildCategories();

    @Query(value = "SELECT brand_id FROM brands_categories WHERE category_id =:category_id", nativeQuery = true)
    List<Integer> findAllBrandIdsByCategoryId(Integer category_id);





}
