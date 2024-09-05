package com.subodh.shopping_app.controller;

import com.subodh.shopping_app.exceptions.ProductNotFoundException;
import com.subodh.shopping_app.exceptions.ResourceNotFoundException;
import com.subodh.shopping_app.model.Product;
import com.subodh.shopping_app.request.AddProductRequest;
import com.subodh.shopping_app.request.ProductUpdateRequest;
import com.subodh.shopping_app.response.ApiResponse;
import com.subodh.shopping_app.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/allproducts")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getSAllProducts();
        return ResponseEntity.ok(new ApiResponse("sucess",products));
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable  Long id){
       try{
           Product product = productService.getProductById(id);
           return ResponseEntity.ok(new ApiResponse("sucess",product));
       }catch(ResourceNotFoundException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
       }


    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product addProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("sucess",addProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/product/{id}/update")
    public  ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long id){
        try {
            Product product = productService.updateProduct(request,id);
            return ResponseEntity.ok(new ApiResponse("Updated",product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{id}/delete")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("deleted",id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        try {
            List<Product> products= productService.getProductByBrandAndName(brandName,productName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse( "No product FOUND" ,null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess!!",products));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()     ));
        }


    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category ,@RequestParam String name) {
        try {
            List<Product> products = productService.getProductByCategoryAndBrand(category, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product FOUND", null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess!!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName( name);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product FOUND", null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess!!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductByBrand( brandName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product FOUND", null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess!!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product FOUND", null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess!!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/products/by/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            Long products = productService.countProductByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse("Sucess!!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }


}
