package com.grocerysearch.controller;

import com.grocerysearch.dto.ProductSearchRequest;
import com.grocerysearch.entity.Product;
import com.grocerysearch.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/create")
    public Product createProduct(@Valid @RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody Product product) {
        return service.updateProduct(productId, product);
    }

    @PatchMapping("/{productId}")
    public Product patchProduct(
            @PathVariable String productId,
            @RequestBody Map<String, Object> updates) {
        return service.patchProduct(productId, updates);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        service.deleteProduct(productId);
        return "Product Deleted Successfully";
    }

    @PostMapping("/filter")
    public Page<Product> filterProducts(
            @RequestBody ProductSearchRequest request) {
        return service.searchProducts(request);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable String productId) {
        return service.getProductById(productId);
    }

    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    @GetMapping("/category")
    public List<Product> searchByCategory(@RequestParam String category) {
        return service.searchByCategory(category);
    }

    @GetMapping("/brand")
    public List<Product> searchByBrand(@RequestParam String brand) {
        return service.searchByBrand(brand);
    }
}
