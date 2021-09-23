package com.canada.aws.dto;

import com.canada.aws.model.Brand;
import com.canada.aws.model.Category;
import com.canada.aws.model.ProductImage;
import com.canada.aws.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDto {
    Integer id;

    private String name;

    private String alias;

    private String fullDescription;

    private Integer quantity;

    private Float discountPrice;

    private Float price;

    private Date discountStart;

    private Date discountEnd;

    private List<CategoryDto> treeCategories;

    private BrandDto brand;

    private CategoryDto category;

    private List<String> images;
}
