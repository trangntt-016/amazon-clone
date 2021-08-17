package com.canada.aws.model;

import com.canada.aws.utils.IdBasedEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="product_images")
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage extends IdBasedEntity {
    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
