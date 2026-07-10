package com.cart.repository;

import com.cart.entity.CartItem;
import com.cart.entity.CartMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {
    Optional<CartItem>
    findByCartMasterAndProductId(
            CartMaster cartMaster,
            String productId);
}
