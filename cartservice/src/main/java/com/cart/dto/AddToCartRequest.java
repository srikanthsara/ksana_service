package com.cart.dto;

import lombok.Data;

@Data
public class AddToCartRequest {

    private String customerId;
    private String customerName;
    private String productId;
    private Integer quantity;

}
