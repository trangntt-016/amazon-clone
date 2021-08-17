package com.canada.aws.model;

import com.canada.aws.utils.IdBasedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Brand  extends IdBasedEntity {
    @Column(nullable = false, length = 45, unique = true)
    private String name;

    @Column(nullable = true, length = 128)
    private String logo;

    @ManyToMany
    @JoinTable(
            name = "brands_categories",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

}
