package com.canada.aws.model;

import com.canada.aws.utils.IdBasedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends IdBasedEntity {
    @Column(length = 256, nullable = false)
    private String name;

    @Column(length = 256, nullable = false)
    private String alias;

    @Column(length = 512, nullable = true, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = true, name = "full_description")
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "quantity")
    private boolean quantity;

    @Column(name = "discount_percent")
    private float discountPercent;

    @Column(name="price")
    private float price;

    @Column(name = "main_image", nullable = false)
    private String mainImage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

}
