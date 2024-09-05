package com.subodh.shopping_app.service.product;

import com.subodh.shopping_app.exceptions.ProductNotFoundException;
import com.subodh.shopping_app.model.Category;
import com.subodh.shopping_app.model.Product;
import com.subodh.shopping_app.repository.CategoryRepository;
import com.subodh.shopping_app.repository.ProductRepository;
import com.subodh.shopping_app.request.AddProductRequest;
import com.subodh.shopping_app.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    @Override
    public Product addProduct(AddProductRequest request) {

        Category category = Optional.ofNullable(categoryRepository.
                        findByName(request.getCategory().getName()))
                .orElseGet( () -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);

        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(request.getName(),
        request.getBranch(),
        request.getPrice(),
        request.getInventary(),
        request.getDescription(),
                request.getCategory()
        );
    }

    @Override
    public List<Product> getSAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
       // productRepository.findById(id).ifPresent(productRepository::delete);
        productRepository.findById(id).
                ifPresentOrElse(productRepository::delete,
                        () -> { throw new ProductNotFoundException("product not found");}) ;
    }

    @Override
    public Product updateProduct(ProductUpdateRequest product, Long productId) {
//       Product newproduct =productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("product not present"));
//       productRepository.save(product);
      return productRepository.findById(productId).
              map(existingProduct -> updateexistingproduct(existingProduct,product))
              .map(productRepository::save)
              .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

    }

    private Product updateexistingproduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBranch());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventary(request.getInventary());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
