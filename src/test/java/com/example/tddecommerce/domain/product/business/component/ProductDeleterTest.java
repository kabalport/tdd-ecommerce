package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

class ProductDeleterTest {

    private IProductRepository iProductRepository;

    private ProductDeleter productDeleter;

    @BeforeEach
    void setUp() {
        iProductRepository = Mockito.mock(IProductRepository.class);
        productDeleter = new ProductDeleter(iProductRepository);
    }

    @Test
    void testDeleteProduct() {
        // Given
        Product product = new Product("Test Product", BigDecimal.valueOf(1000), "Test Description", DiscountPolicy.NONE);
        product.setDelFlag(false); // 초기 delFlag 값이 false인 상태

        // When
        productDeleter.deleteProduct(product);

        // Then
        assertTrue(product.isDelFlag()); // delFlag가 true로 설정되었는지 검증
        verify(iProductRepository, times(1)).save(product); // save 메서드가 호출되었는지 확인
    }
}
