package com.canada.aws.utils;

import com.canada.aws.model.Category;
import com.canada.aws.repo.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootTest
public class ProcessDataUtils {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void setParentIdForCategories() throws FileNotFoundException {
        List<Category> categories = categoryRepository.findAll();

        Scanner sc = new Scanner(new File("D:\\Projects\\amazon\\backend\\src\\main\\resources\\patterntree.csv"));
        sc.useDelimiter(",");   //sets the delimiter pattern

        while (sc.hasNext())  //returns a boolean value
        {
            String strLine = sc.next();
            if(!strLine.equals("")){
                String[] tokens = strLine.split(">>");
                if(tokens.length > 3){
                    String sub2 = tokens[2].replace("\"","").replace("]","").trim();
                    Optional<Category> SubTwo = categoryRepository.findCategoryByName(sub2);
                    if(SubTwo.isPresent()) {
                        String sub3 = tokens[3].replace("\"","").replace("]","").trim();
                        Optional<Category> SubThree = categoryRepository.findCategoryByName(sub3);
                        if(SubThree.isPresent() && SubThree.get().getParent()==null){
                            SubThree.get().setParent(SubTwo.get());
                            categoryRepository.save(SubThree.get());
                        }
                    }
                }
            }

        }
        sc.close();  //closes the scanner
    }

    @Test
    public void setIsHasChildren(){
        List<Category>categories = new ArrayList<>();

        categories.forEach(c -> {
            if(c.getChildren().size()==0){
                c.setIsHasChildren(false);
                this.categoryRepository.save(c);
            }
        });

    }
}
