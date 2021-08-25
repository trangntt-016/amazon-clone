package com.canada.aws.model;

import com.canada.aws.utils.IdBasedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="product_images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductImage extends IdBasedEntity {
    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductImage other = (ProductImage) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        } else if (!this.id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("image id:").append(this.id)
                .append(",url:").append(this.name);

        return sb.toString();
    }

}
