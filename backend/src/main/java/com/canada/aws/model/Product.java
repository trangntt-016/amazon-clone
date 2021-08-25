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

    @Column(length = 4096, name = "full_description")
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "discount_price")
    private Float discountPrice;

    @Column(name="price")
    private Float price;

    @Column(name = "main_image", nullable = true)
    private String mainImage;

    @Column(name="discount_start")
    private Date discountStart;

    @Column(name="discount_end")
    private Date discountEnd;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> extraImages = new HashSet<>();

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("product id:").append(this.id)
                .append(",name:").append(this.name);

        return sb.toString();
    }
}
