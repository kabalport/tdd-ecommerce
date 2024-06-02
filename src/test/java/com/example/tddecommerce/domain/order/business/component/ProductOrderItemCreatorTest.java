package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.component.ProductReader;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductOrderItemCreatorTest {

    @Mock
    private ProductReader productReader;

    @InjectMocks
    private ProductOrderItemCreator productOrderItemCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrepareOrderItems() {
        // Given
        ProductOrderDetail detail1 = new ProductOrderDetail(1L, 2);
        ProductOrderDetail detail2 = new ProductOrderDetail(2L, 3);

        Product product1 = new Product(1L, "Product 1", BigDecimal.valueOf(100), "Description 1", null, false);
        Product product2 = new Product(2L, "Product 2", BigDecimal.valueOf(200), "Description 2", null, false);

        when(productReader.execute(1L)).thenReturn(Optional.of(product1));
        when(productReader.execute(2L)).thenReturn(Optional.of(product2));

        // When
        List<ProductOrderItem> orderItems = productOrderItemCreator.prepareOrderItems(Arrays.asList(detail1, detail2));

        // Then
        assertEquals(2, orderItems.size());

        ProductOrderItem item1 = orderItems.get(0);
        assertEquals(1L, item1.getProduct().getId());
        assertEquals(2, item1.getQuantity());
        assertEquals(BigDecimal.valueOf(200), item1.getPrice());

        ProductOrderItem item2 = orderItems.get(1);
        assertEquals(2L, item2.getProduct().getId());
        assertEquals(3, item2.getQuantity());
        assertEquals(BigDecimal.valueOf(600), item2.getPrice());

        verify(productReader, times(1)).execute(1L);
        verify(productReader, times(1)).execute(2L);
    }

    @Test
    void testPrepareOrderItemsProductNotFound() {
        // Given
        ProductOrderDetail detail = new ProductOrderDetail(1L, 2);

        when(productReader.execute(1L)).thenReturn(Optional.empty());

        // When & Then
        ProductException exception = assertThrows(ProductException.class, () -> {
            productOrderItemCreator.prepareOrderItems(Arrays.asList(detail));
        });

        assertEquals("Product not found: 1", exception.getMessage());

        verify(productReader, times(1)).execute(1L);
    }
}
