package com.subodh.shopping_app.controller;

import com.subodh.shopping_app.dto.ImageDto;

import com.subodh.shopping_app.exceptions.ResourceNotFoundException;

import com.subodh.shopping_app.model.Image;
import com.subodh.shopping_app.response.ApiResponse;
import com.subodh.shopping_app.service.image.ImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files ,
                                                  @RequestParam Long productId){

     try{
         List<ImageDto> imageDtos = imageService.saveImages(files,productId);
         return ResponseEntity.ok(new ApiResponse("upload sucessful",imageDtos));
     }catch (Exception e){
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body( new ApiResponse("Upload Failed",e.getMessage()));
     }

    }
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId ) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFiletype()))
                .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"" +image.getFilename() + "\"")
                .body( resource);
    }
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update Successful", null));
            }
        } catch (ResourceNotFoundException e) { // Corrected exception name
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null)); // Fixed method usage
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Fixed status usage
                .body(new ApiResponse("Update Failed", null));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById (imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Successful", null));
            }
        } catch (ResourceNotFoundException e) { // Corrected exception name
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null)); // Fixed method usage
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Fixed status usage
                .body(new ApiResponse("Delete Failed", null));
    }

}
