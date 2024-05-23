package com.example.tddecommerce.domain.product.business.domain;

import com.example.tddecommerce.domain.product.application.service.ProductException;
import com.example.tddecommerce.domain.product.domain.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.domain.model.Product;
import com.example.tddecommerce.domain.product.domain.model.ProductStock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductStock 도메인 테스트")
class ProductStockTest {

    @Test
    @DisplayName("재고 감소 테스트")
    void decreaseStock() {
        // given
        Product product = new Product("상품명", BigDecimal.valueOf(1000), "상품 설명", DiscountPolicy.NONE);
        ProductStock productStock = new ProductStock(product, 10);

        // when
        productStock.decrease(3);

        // then
        assertThat(productStock
                .getQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("재고 감소 시 0개 미만 예외 테스트")
    void decreaseStockBelowZero() {
        // given
        Product product = new Product("상품명", BigDecimal.valueOf(1000), "상품 설명", DiscountPolicy.NONE);
        ProductStock productStock = new ProductStock(product, 5);

        // when, then
        assertThatThrownBy(() -> productStock.decrease(6))
                .isInstanceOf(ProductException.class)
                .hasMessage("재고는 0개 미만이 될 수 없습니다.");
    }

    @Test
    @DisplayName("재고 감소 후 마지막 업데이트 시간 갱신 테스트")
    void decreaseStockUpdatesLastUpdated() {
        // given
        Product product = new Product("상품명", BigDecimal.valueOf(1000), "상품 설명", DiscountPolicy.NONE);
        ProductStock productStock = new ProductStock(product, 10);

        LocalDateTime beforeUpdate = productStock.getLastUpdated();

        // 잠시 대기하여 시간을 미세하게 지연시킵니다.
        try {
            Thread.sleep(1); // 1 밀리초 지연
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // when
        productStock.decrease(2);

        // then
        LocalDateTime afterUpdate = productStock.getLastUpdated();
        assertThat(productStock.getQuantity()).isEqualTo(8);
        assertThat(afterUpdate).isAfter(beforeUpdate);
    }


    @Test
    @DisplayName("생성 시 마지막 업데이트 시간 초기화 테스트")
    void creationInitializesLastUpdated() {
        // given
        Product product = new Product("상품명", BigDecimal.valueOf(1000), "상품 설명", DiscountPolicy.NONE);

        // when
        ProductStock productStock = new ProductStock(product, 10);

        // then
        assertThat(productStock.getLastUpdated()).isNotNull();
    }
}
