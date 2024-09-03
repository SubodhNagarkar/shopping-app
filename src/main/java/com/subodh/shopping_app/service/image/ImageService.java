package com.subodh.shopping_app.service.image;

import com.subodh.shopping_app.dto.ImageDto;
import com.subodh.shopping_app.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file ,Long imageId);
}
