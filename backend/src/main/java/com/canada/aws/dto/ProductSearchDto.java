package com.canada.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchDto {
    private Integer id;

    private String name;

    private String alias;

    private float price;

    private float discountPrice;

    private Date discountStart;

    private Date discountEnd;

    private String mainImage;
}
