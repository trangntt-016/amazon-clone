package com.canada.aws.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String name;

    private String fullDescription;

    private Date createdTime;

    private Integer quantity;

    private float price;

    private float discountPrice;

    private Date discountStart;

    private Date discountEnd;

    private MultipartFile mainImage;

    private MultipartFile[] extraImages;

    private Integer category_id;

    private Integer brand_id;

}
