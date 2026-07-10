package com.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    private String productId;

    private String productName;

    private String category;

    private String brand;

    private BigDecimal price;

    private BigDecimal gstPercentage;

    private Integer availableQuantity;

    private Boolean inStock;
}
