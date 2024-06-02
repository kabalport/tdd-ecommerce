package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockReader;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockUpdater;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductStockManagerTest {

    @Mock
    private ProductStockUpdater productStockUpdater;

    @Mock
    private ProductStockReader productStockReader;

    @InjectMocks
    private ProductStockManager productStockManager;

    private Product product1;
    private Product product2;
    private ProductStock productStock1;
    private ProductStock productStock2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product1 = new Product("Product 1", BigDecimal.valueOf(100), "Description 1", null);
        product2 = new Product("Product 2", BigDecimal.valueOf(200), "Description 2", null);
        productStock1 = new ProductStock(product1, 10);
        productStock2 = new ProductStock(product2, 5);
    }

    @Test
    void testManageProductStockSuccess() {
        // Given
        ProductOrderItem item1 = new ProductOrderItem(product1, 2, BigDecimal.valueOf(200));
        ProductOrderItem item2 = new ProductOrderItem(product2, 3, BigDecimal.valueOf(600));
        List<ProductOrderItem> items = Arrays.asList(item1, item2);

//        when(productStockReader.getProductStock(product1)).thenReturn(productStock1);
//        when(productStockReader.getProductStock(product2)).thenReturn(productStock2);

        // When
        Map<Product, ProductStock> result = productStockManager.manageProductStock(items);

        // Then
        assertEquals(2, result.size());
        assertEquals(8, productStock1.getQuantity());
        assertEquals(2, productStock2.getQuantity());

//        verify(productStockUpdater, times(1)).decreaseStock(productStock1, 2);
//        verify(productStockUpdater, times(1)).decreaseStock(productStock2, 3);
    }

    @Test
    void testManageProductStockInsufficientStock() {
        // Given
        ProductOrderItem item1 = new ProductOrderItem(product1, 20, BigDecimal.valueOf(2000));
        List<ProductOrderItem> items = Arrays.asList(item1);

//        when(productStockReader.getProductStock(product1)).thenReturn(productStock1);

        // When & Then
        ProductException exception = assertThrows(ProductException.class, () -> {
            productStockManager.manageProductStock(items);
        });

        assertEquals("Insufficient stock for product: " + product1.getId(), exception.getMessage());
//        verify(productStockUpdater, never()).decreaseStock(any(ProductStock.class), anyInt());
    }
}
