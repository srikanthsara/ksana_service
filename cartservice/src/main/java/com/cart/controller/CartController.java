package com.cart.controller;

import com.cart.dto.AddToCartRequest;
import com.cart.entity.CartMaster;
import com.cart.service.CartService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;
    // ADD TO CART
    @PostMapping("/add")
    public CartMaster addToCart(
            @Valid
            @RequestBody AddToCartRequest request) {
        return service.addToCart(request);
    }


    // GET CUSTOMER CART
    @GetMapping("/{customerId}")
    public ResponseEntity<CartMaster> getCart(
            @PathVariable String customerId) {
        return ResponseEntity.ok(service.getCart(customerId));
    }

    @PutMapping("/item/{cartItemId}")
    public CartMaster updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        return service.updateQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/item/{cartItemId}")
    public String removeItem(
            @PathVariable Long cartItemId) {
        service.removeItem(cartItemId);
        return "Item Removed Successfully";
    }

    @DeleteMapping("/clear/{customerId}")
    public String clearCart(
            @PathVariable String customerId) {
        service.clearCart(customerId);
        return "Cart Cleared Successfully";
    }
}
