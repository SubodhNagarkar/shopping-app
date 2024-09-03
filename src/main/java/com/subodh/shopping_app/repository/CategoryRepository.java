package com.subodh.shopping_app.repository;

import com.subodh.shopping_app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);

    boolean existsByName(String name);
}
