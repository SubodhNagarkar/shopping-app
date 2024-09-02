package com.subodh.shopping_app.request;

import com.subodh.shopping_app.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class AddProductRequest {

    private Long id;
    private String name;
    private String branch;
    private BigDecimal price;
    private int inventary;
    private String description;
    private Category category;
}
