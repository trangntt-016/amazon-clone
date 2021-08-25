package com.canada.aws.repo;

import com.canada.aws.model.BrowsingCategoryHistory;
import com.canada.aws.model.Category;
import com.canada.aws.model.UserEntity;
import com.canada.aws.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Set;

@SpringBootTest
public class UserEntityRepositoryTests {
    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityUtils utils;

    @Test
    public void testAddBrowsingHistoryToUser(){
        Category category = utils.generateRandomEntity(categoryRepository, categoryRepository.findAll().get(0).getId());

        UserEntity randomUser = userRepository.findById("C_002").get();

        Set<BrowsingCategoryHistory> historySet = randomUser.getBrowsing_histories();
        BrowsingCategoryHistory history = BrowsingCategoryHistory.builder()
                .category_id(category.getId())
                .search_on(new Date())
                .build();

        historySet.add(history);
        randomUser.setBrowsing_histories(historySet);
        userRepository.save(randomUser);

    }
}
