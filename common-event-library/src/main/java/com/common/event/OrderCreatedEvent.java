package com.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;
    private String customerId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String paymentType;
    private String paymentProvider;
    private LocalDateTime createdAt;
}