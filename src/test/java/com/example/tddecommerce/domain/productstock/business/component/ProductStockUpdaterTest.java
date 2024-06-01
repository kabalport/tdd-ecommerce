package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductStockUpdaterTest {

    @Mock
    private IProductStockRepository productStockRepository;

    @InjectMocks
    private ProductStockUpdater productStockUpdater;

    private ProductStock productStock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productStock = new ProductStock();
        productStock.increase(100);
    }

    @Test
    void testIncreaseStock() {
        // Given
        when(productStockRepository.save(productStock)).thenReturn(productStock);

        // When
        ProductStock updatedStock = productStockUpdater.increaseStock(productStock, 50);

        // Then
        assertThat(updatedStock.getQuantity()).isEqualTo(150);
        verify(productStockRepository, times(1)).save(productStock);
    }

    @Test
    void testDecreaseStock() {
        // Given
        when(productStockRepository.save(productStock)).thenReturn(productStock);

        // When
        ProductStock updatedStock = productStockUpdater.decreaseStock(productStock, 50);

        // Then
        assertThat(updatedStock.getQuantity()).isEqualTo(50);
        verify(productStockRepository, times(1)).save(productStock);
    }

    @Test
    void testDecreaseStock_InsufficientQuantity() {
        // Given
        productStock.increase(20);

        // When & Then
        ProductException exception = assertThrows(ProductException.class, () -> productStockUpdater.decreaseStock(productStock, 50));
        assertThat(exception.getMessage()).contains("재고는 0개 미만이 될 수 없습니다.");
        verify(productStockRepository, never()).save(productStock);
    }
}
