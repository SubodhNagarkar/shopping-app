package com.subodh.shopping_app.repository;

import com.subodh.shopping_app.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
