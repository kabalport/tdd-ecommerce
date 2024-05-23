package com.example.tddecommerce.domain.product.business.domain;

import com.example.tddecommerce.domain.product.domain.model.DiscountPolicy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

    @Test
    void noneDiscountPolicy() {
        final BigDecimal price = BigDecimal.valueOf(1000);

        final BigDecimal discountedPrice = DiscountPolicy.NONE.applyDiscount(price);

        assertThat(discountedPrice).isEqualTo(price);
    }

    @Test
    void fix_1000_discounted_price() {
        final BigDecimal price = BigDecimal.valueOf(2000);

        final BigDecimal discountedPrice = DiscountPolicy.FIX_1000_AMOUNT.applyDiscount(price);

        assertThat(discountedPrice).isEqualTo(BigDecimal.valueOf(1000));
    }

    @Test
    void over_discounted_price() {
        final BigDecimal price = BigDecimal.valueOf(500);

        final BigDecimal discountedPrice = DiscountPolicy.FIX_1000_AMOUNT.applyDiscount(price);

        assertThat(discountedPrice).isEqualTo(BigDecimal.ZERO);
    }
}
