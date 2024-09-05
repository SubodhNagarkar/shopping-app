package com.subodh.shopping_app.service.image;

import com.subodh.shopping_app.dto.ImageDto;
import com.subodh.shopping_app.exceptions.ResourceNotFoundException;

import com.subodh.shopping_app.model.Image;
import com.subodh.shopping_app.model.Product;
import com.subodh.shopping_app.repository.ImageRepository;
import com.subodh.shopping_app.service.product.ProductService;
import com.subodh.shopping_app.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements  ImageService{


    private final ImageRepository imageRepository;

    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.
                findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Image not found at Id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> { throw  new ResourceNotFoundException("Image not found at Id " + id); } );
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file : files){
            try{
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFiletype(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/download/";
                String downloadUrl =buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFilename());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);
            }catch (IOException | SQLException  e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try{
            image.setFilename(file.getOriginalFilename());
            image.setFilename(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch (IOException | SQLException e){
            throw  new RuntimeException(e.getMessage());
        }
    }
}
