package com.common.services;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceCalculationService {

    public BigDecimal calculateItemTotal(BigDecimal unitPrice, Integer quantity) {

        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateGST(
            BigDecimal amount,
            BigDecimal gstPercentage) {

        if (gstPercentage == null) {
            return BigDecimal.ZERO;
        }

        return amount
                .multiply(gstPercentage)
                .divide(
                        BigDecimal.valueOf(100),
                        2,
                        RoundingMode.HALF_UP
                );
    }

    public BigDecimal calculateFinalAmount(
            BigDecimal amount,
            BigDecimal gstAmount) {

        return amount.add(gstAmount);
    }

    public BigDecimal calculateOrderTotal(
            BigDecimal subTotal,
            BigDecimal gst,
            BigDecimal shipping,
            BigDecimal discount) {

        return subTotal
                .add(gst)
                .add(shipping)
                .subtract(discount);
    }
}
