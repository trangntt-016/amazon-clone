package com.canada.aws.utils;

import com.canada.aws.model.Category;
import com.canada.aws.model.Product;
import com.canada.aws.repo.CategoryRepository;
import com.canada.aws.repo.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@SpringBootTest
public class ProcessDataUtils {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

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
    public void setParentIdListForCategories(){
        List<Category> categories = categoryRepository.findLastChildCategories();

        categories.forEach(category -> {
            Category root = category;
            List<Integer>list = new ArrayList<>();
            while(category.getParent()!=null){
                category = category.getParent();
                list.add(category.getId());
            }
            Collections.reverse(list);
            root.setAllParentIDs(toString(list));
            categoryRepository.save(root);
        });
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

    @Test
    public void updateCreatedTimeProduct(){
        List<Product>products = productRepository.findAll();

        products.forEach(p -> {
            p.setCreatedTime(new Date());
            productRepository.save(p);
        });
    }

    public String toString(List<Integer> list){
        StringBuilder sb = new StringBuilder();
        for(Integer l: list){
            sb.append(l.toString()).append(",");
        }
        sb = sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    @Test
    public void modifyData(){

            List<Product> all = productRepository.findAllByCategoryId(12);
            for(Product p: all){
                if(p.getPrice()>100 || p.getDiscountPrice()>100){
                    Float newprice = p.getPrice()/10;
                    p.setPrice(newprice);

                    Float newDiscount = p.getDiscountPrice();
                    if(newDiscount!=null){
                        p.setDiscountPrice(newDiscount/10);
                    }
                    productRepository.save(p);
                }


            }


    }
}
