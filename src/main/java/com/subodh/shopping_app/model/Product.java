package com.subodh.shopping_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String branch;
    private BigDecimal price;
    private int inventary;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;

    public Product(String name, String branch, BigDecimal price, int inventary, String description, Category category) {
        this.name = name;
        this.branch = branch;
        this.price = price;
        this.inventary = inventary;
        this.description = description;
        this.category = category;
    }
}
