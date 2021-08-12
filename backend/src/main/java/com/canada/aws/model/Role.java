package com.canada.aws.model;

import com.canada.aws.utils.IdBasedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends IdBasedEntity {
    @Column(name = "role_name",length = 20,nullable = false, unique = true)
    private String roleName;

    @Column(name = "code",length = 5,nullable = false, unique = true)
    private String code;
}