package com.example.tddecommerce.domain.product.business.domain;

import com.example.tddecommerce.domain.product.domain.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 도메인 테스트")
class ProductTest {

    @Test
    @DisplayName("상품 정보 업데이트 테스트")
    void update() {
        final Product product = new Product("상품명", BigDecimal.valueOf(1000), "상품 설명", DiscountPolicy.NONE);

        product.update("상품 수정", BigDecimal.valueOf(2000), "상품 설명 수정", DiscountPolicy.NONE);

        assertThat(product.getName()).isEqualTo("상품 수정");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(2000));
        assertThat(product.getDescription()).isEqualTo("상품 설명 수정");
    }

    @Test
    @DisplayName("할인이 적용되지 않은 상품 가격 테스트")
    void none_discounted_product() {
        final Product product = new Product("상품명", BigDecimal.valueOf(1000), "상품 설명", DiscountPolicy.NONE);

        final BigDecimal discountedPrice = product.getDiscountedPrice();

        assertThat(discountedPrice).isEqualTo(BigDecimal.valueOf(1000));
    }

    @Test
    @DisplayName("1000원 할인이 적용된 상품 가격 테스트")
    void fix_1000_discounted_product() {
        final Product product = new Product("상품명", BigDecimal.valueOf(1000), "상품 설명", DiscountPolicy.FIX_1000_AMOUNT);

        final BigDecimal discountedPrice = product.getDiscountedPrice();

        assertThat(discountedPrice).isEqualTo(BigDecimal.ZERO);
    }
}
