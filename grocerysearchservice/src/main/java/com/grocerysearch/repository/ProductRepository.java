package com.grocerysearch.repository;

import com.grocerysearch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> ,
        JpaSpecificationExecutor<Product> {

    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByBrandIgnoreCase(String brand);
}
