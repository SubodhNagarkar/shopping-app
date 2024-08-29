package com.subodh.shopping_app.service.product;

import com.subodh.shopping_app.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);
    List<Product> getSAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    void updateProduct(Product product ,Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category ,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductByBrandAndName(String brand,String name);
    Long countProductByBrandAndName(String brand,String name);



}
