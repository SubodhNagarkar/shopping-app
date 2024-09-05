package com.subodh.shopping_app.controller;

import com.subodh.shopping_app.exceptions.AlreadyExistsException;
import com.subodh.shopping_app.exceptions.ResourceNotFoundException;
import com.subodh.shopping_app.model.Category;
import com.subodh.shopping_app.response.ApiResponse;
import com.subodh.shopping_app.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){

        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found",categories));
    }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("failed",HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @PostMapping("/addcategory")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Added", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));

        }
    }

    @GetMapping("/category/{id}/category")
    public  ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try{
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found",category));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}/category")
    public  ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found",category));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping ("/category/{id}/delete")
    public  ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try{
             categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Delete",null));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping ("/category/{id}/update")
    public  ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try{
            Category category1 =categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Updated",category1));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
