package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductReaderTest {

    @Mock
    private IProductRepository iProductRepository;

    private ProductReader productReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productReader = new ProductReader(iProductRepository);
    }

    @Test
    void testExecuteWithNonDeletedProduct() {
        // Given
        Long productId = 1L;
        Product activeProduct = new Product("Active Product", new BigDecimal("200.00"), "Active Description", DiscountPolicy.NONE);
        activeProduct.setDelFlag(false);
        when(iProductRepository.findByProductId(productId)).thenReturn(Optional.of(activeProduct));

        // When
        Optional<Product> result = productReader.execute(productId);

        // Then
        assertTrue(result.isPresent(), "Active product should be returned");
    }

    @Test
    void testExecuteWithDeletedProduct() {
        // Given
        Long productId = 2L;
        Product deletedProduct = new Product("Deleted Product", new BigDecimal("50.00"), "Deleted Description", DiscountPolicy.NONE);
        deletedProduct.setDelFlag(true);
        when(iProductRepository.findByProductId(productId)).thenReturn(Optional.of(deletedProduct));

        // When
        Optional<Product> result = productReader.execute(productId);

        // Then
        assertFalse(result.isPresent(), "Deleted product should not be returned");
    }
}
