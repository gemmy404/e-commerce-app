package com.app.furniture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private String description;
    private Double price;
    private String image;
    private Integer stockQuantity;
    private Boolean isAvailable;
    @Transient
    private Boolean isFavorite;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    public Boolean getIsAvailable() {
        return stockQuantity > 0;
    }

}
