package com.example.tddecommerce.domain.product.application;

import com.example.tddecommerce.domain.product.business.component.*;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockCreator;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@Transactional
class ProductServiceTest extends IntegrationTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCreator productCreator;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        // 상품 생성 및 저장
        testProduct = new Product("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE);
        productCreator.save(testProduct);
    }

    @Test
    void testAddProduct() {
        // When
        Product result = productService.createProduct("New Product", BigDecimal.valueOf(200), "New Description", DiscountPolicy.FIX_1000_AMOUNT);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("New Product");
        assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(200));
        assertThat(result.getDescription()).isEqualTo("New Description");

    }

    @Test
    void testGetProduct() {
        // When
        Product result = productService.getProductById(testProduct.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Product");
        assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(100));

        System.out.println(testProduct.getId());
        System.out.println(testProduct.getName());
        System.out.println(testProduct.getPrice());
    }

    @Test
    void testGetProduct_ProductNotFound() {
        // Given
        Long nonExistentProductId = 999L;

        // When & Then
        Exception exception = assertThrows(ProductException.class, () -> productService.getProductById(nonExistentProductId));
        assertThat(exception.getMessage()).contains("Product not found or deleted: " + nonExistentProductId);
    }

    @Test
    void testUpdateProduct() {
        // When
        Product result = productService.updateProduct(testProduct.getId(), "Updated Product", BigDecimal.valueOf(150), "Updated Description", DiscountPolicy.FIX_1000_AMOUNT);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Product");
        assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(150));
        assertThat(result.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    void testDeleteProduct() {
        // When
        productService.deleteProduct(testProduct.getId());

        // Then
        assertThrows(ProductException.class, () -> productService.getProductById(testProduct.getId()));
    }
}
