package com.example.tddecommerce.domain.product.business.model;

import java.math.BigDecimal;

public enum DiscountPolicy {
    NONE {
        @Override
        public BigDecimal applyDiscount(final BigDecimal price) {
            return price;
        }
    },
    FIX_1000_AMOUNT {
        @Override
        public BigDecimal applyDiscount(final BigDecimal price) {
            return price.subtract(BigDecimal.valueOf(1000)).max(BigDecimal.ZERO);
        }
    };

    public abstract BigDecimal applyDiscount(final BigDecimal price);
}
