package com.grocerysearch.service;

import com.grocerysearch.dto.ProductSearchRequest;
import com.grocerysearch.entity.Product;
import com.grocerysearch.repository.ProductRepository;
import com.grocerysearch.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }


    public Product updateProduct(String productId, Product updatedProduct) {

        Product existingProduct = getProductById(productId);
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCurrency(updatedProduct.getCurrency());
        existingProduct.setAvailableQuantity(updatedProduct.getAvailableQuantity());
        existingProduct.setRating(updatedProduct.getRating());
        existingProduct.setInStock(updatedProduct.getInStock());

        return repository.save(existingProduct);
    }


    // DYNAMIC SEARCH
    public Page<Product> searchProducts(
            ProductSearchRequest request) {

        Sort sort = Sort.by(

                Sort.Direction.fromString(
                        request.getSortDirection()),

                request.getSortBy()
        );

        Pageable pageable = PageRequest.of(

                request.getPageNumber(),

                request.getPageSize(),

                sort
        );

        return repository.findAll(ProductSpecification.searchProducts(request),
                pageable
        );
    }
    public Product patchProduct(
            String productId, Map<String, Object> updates) {

        Product existingProduct = getProductById(productId);
        updates.forEach((field, value) -> {

            Field declaredField =
                    org.springframework.util.ReflectionUtils
                            .findField(Product.class, field);

            if (declaredField != null) {
                declaredField.setAccessible(true);
                org.springframework.util.ReflectionUtils
                        .setField(declaredField, existingProduct, value);
            }
        });

        return repository.save(existingProduct);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(String productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    public List<Product> searchByName(String name) {
        return repository.findByProductNameContainingIgnoreCase(name);
    }

    public List<Product> searchByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    public List<Product> searchByBrand(String brand) {
        return repository.findByBrandIgnoreCase(brand);
    }

    public void deleteProduct(String productId) {
        Product existingProduct = getProductById(productId);
        repository.delete(existingProduct);
    }
}
