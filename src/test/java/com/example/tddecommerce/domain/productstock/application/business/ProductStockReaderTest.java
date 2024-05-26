package com.example.tddecommerce.domain.productstock.application.business;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.productstock.application.model.ProductStock;
import com.example.tddecommerce.domain.productstock.application.repository.IProductStockRepository;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductStockReaderTest {

    @Mock
    private IProductStockRepository productStockRepository;

    @InjectMocks
    private ProductStockReader productStockReader;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testProduct = new Product("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE);
    }

    @Test
    void testGetProductStock() {
        // Given
        ProductStock productStock = new ProductStock(testProduct, 100);
        when(productStockRepository.findByProduct(testProduct)).thenReturn(Optional.of(productStock));

        // When
        ProductStock foundProductStock = productStockReader.getProductStock(testProduct);

        // Then
        assertThat(foundProductStock).isNotNull();
        assertThat(foundProductStock.getQuantity()).isEqualTo(100);
        assertThat(foundProductStock.getProduct()).isEqualTo(testProduct);
        verify(productStockRepository, times(1)).findByProduct(testProduct);
    }

    @Test
    void testGetProductStock_ProductStockNotFound() {
        // Given
        when(productStockRepository.findByProduct(testProduct)).thenReturn(Optional.empty());

        // When & Then
        ProductException exception = assertThrows(ProductException.class, () -> productStockReader.getProductStock(testProduct));
        assertThat(exception.getMessage()).contains("Product stock not found for product id: " + testProduct.getId());
        verify(productStockRepository, times(1)).findByProduct(testProduct);
    }
}
