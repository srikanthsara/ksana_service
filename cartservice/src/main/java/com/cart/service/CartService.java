package com.cart.service;

import com.cart.client.GrocerySearchClient;
import com.cart.dto.AddToCartRequest;
import com.cart.dto.Product;
import com.cart.entity.CartItem;
import com.cart.entity.CartMaster;
import com.cart.repository.CartItemRepository;
import com.cart.repository.CartMasterRepository;


import com.common.services.PriceCalculationService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMasterRepository masterRepository;
    private final CartItemRepository itemRepository;
    private final GrocerySearchClient groceryClient;
    private final PriceCalculationService priceCalculationService;

    // ADD TO CART
    @Transactional
    public CartMaster addToCart(AddToCartRequest request) {

        // FIND CUSTOMER CART
        CartMaster cart = masterRepository
                .findByCustomerId(request.getCustomerId())
                .orElse(null);

        Product product =
                groceryClient.getProductById(request.getProductId());

        if (product == null) {
            throw new RuntimeException(
                    "Product Not Found : "
                            + request.getProductId());
        }

        // CREATE NEW CART IF NOT EXISTS
        if (cart == null) {

            cart = CartMaster.builder()
                    .customerId(request.getCustomerId())
                    .customerName(request.getCustomerName())
                    .createdAt(LocalDateTime.now())
                    .totalAmount(BigDecimal.ZERO)
                    .totalItems(0)
                    .cartItems(new ArrayList<>())
                    .build();

            cart = masterRepository.save(cart);
        }

        // CHECK PRODUCT ALREADY EXISTS IN CART
        Optional<CartItem> existingItem =
                itemRepository.findByCartMasterAndProductId(cart, request.getProductId());


        BigDecimal totalPrice =  priceCalculationService
                        .calculateItemTotal(product.getPrice(), request.getQuantity());

        BigDecimal gstAmount =   priceCalculationService
                        .calculateGST(totalPrice, product.getGstPercentage());


        // PRODUCT ALREADY EXISTS
        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            // UPDATE QUANTITY
            item.setQuantity(
                    item.getQuantity()
                            + request.getQuantity());

            // UPDATE TOTAL PRICE

            totalPrice =
                    priceCalculationService
                            .calculateItemTotal(item.getUnitPrice(), item.getQuantity()
                                    + request.getQuantity());

            gstAmount =
                    priceCalculationService
                            .calculateGST(
                                    totalPrice,
                                    item.getGstPercentage());

            item.setTotalPrice(totalPrice);

            item.setGstAmount(gstAmount);

        } else {

            CartItem item = CartItem.builder()

                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .category(product.getCategory())
                    .brand(product.getBrand())
                    .quantity(request.getQuantity())
                    .unitPrice(product.getPrice())
                    .totalPrice(
                            product.getPrice()
                                    .multiply(BigDecimal.valueOf(request.getQuantity()))
                    )
                    .totalPrice(totalPrice)
                    .gstPercentage(product.getGstPercentage())
                    .gstAmount(gstAmount)
                    .build();

            item.setCartMaster(cart);
            itemRepository.save(item);
            cart.getCartItems().add(item);

        }

        // RECALCULATE CART
        recalculateCart(cart);
        return masterRepository.save(cart);
    }

    // GET CART
    public CartMaster getCart(String customerId) {

        return masterRepository
                .findByCustomerId(customerId)
                .orElseGet(() ->

                        CartMaster.builder()
                                .customerId(customerId)
                                .customerName("")
                                .totalAmount(BigDecimal.ZERO)
                                .totalItems(0)
                                .cartItems(new ArrayList<>())
                                .build()
                );
    }

    @Transactional
    public CartMaster updateQuantity(
            Long cartItemId,
            Integer quantity) {

        CartItem item = itemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart Item Not Found"));

        item.setQuantity(quantity);


        BigDecimal totalPrice =
                priceCalculationService.calculateItemTotal(item.getUnitPrice(), quantity);

        BigDecimal gstAmount =
                priceCalculationService.calculateGST(totalPrice, item.getGstPercentage());

        item.setTotalPrice(totalPrice);

        item.setGstAmount(gstAmount);
        itemRepository.save(item);

        CartMaster cart = item.getCartMaster();

        recalculateCart(cart);

        masterRepository.save(cart);

        return getCart(cart.getCustomerId());
    }

    // REMOVE ITEM
    @Transactional
    public void removeItem(Long cartItemId) {

        CartItem item = itemRepository

                .findById(cartItemId)

                .orElseThrow(() ->

                        new RuntimeException(
                                "Cart Item Not Found"));

        CartMaster cart = item.getCartMaster();
        cart.getCartItems().remove(item);
        itemRepository.delete(item);
        recalculateCart(cart);
        masterRepository.save(cart);
    }

    // HELPER METHOD
    private void addCartItem(
            CartMaster cart,
            CartItem item) {

        cart.getCartItems().add(item);
        item.setCartMaster(cart);
    }

    // RECALCULATE CART
    private void recalculateCart(
            CartMaster cart) {

        BigDecimal totalAmount = cart.getCartItems()

                .stream()
                .map(item ->
                        priceCalculationService
                                .calculateFinalAmount(
                                        item.getTotalPrice(),
                                        item.getGstAmount()
                                )
                )

                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        cart.setTotalAmount(totalAmount);

        cart.setTotalItems(
                cart.getCartItems().size());
    }


    @Transactional
    public void clearCart(String customerId) {

        CartMaster cart = masterRepository
                .findByCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cart Not Found"));

        cart.getCartItems().clear();

        cart.setTotalAmount(BigDecimal.ZERO);

        cart.setTotalItems(0);

        masterRepository.save(cart);
    }
}
