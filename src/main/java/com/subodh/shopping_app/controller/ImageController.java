package com.subodh.shopping_app.controller;

import com.subodh.shopping_app.dto.ImageDto;
import com.subodh.shopping_app.response.ApiResponse;
import com.subodh.shopping_app.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
