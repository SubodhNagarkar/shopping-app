package com.subodh.shopping_app.service.product;

import com.subodh.shopping_app.model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService{
    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getSAllProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return List.of();
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return 0;
    }
}
