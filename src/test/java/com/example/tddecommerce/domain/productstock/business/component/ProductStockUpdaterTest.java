package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductStockUpdaterTest {

    private IProductStockRepository productStockRepository;
    private IProductRepository productRepository;
    private ProductStockUpdater productStockUpdater;

    Product testProduct;

    ProductStock testProductStock;
    Long testProductId;

    @BeforeEach
    void setUp() {
        productStockRepository = Mockito.mock(IProductStockRepository.class);
        productRepository = Mockito.mock(IProductRepository.class);
        productStockUpdater = new ProductStockUpdater(productStockRepository, productRepository);

        testProductId = 1L;
        testProduct = new Product("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE);
        testProductStock = new ProductStock(testProduct,50);
    }

    @Test
    void setStock_Successful() {
        when(productRepository.findByProductId(testProductId)).thenReturn(Optional.of(testProduct));
        when(productStockRepository.findByProductId(testProductId)).thenReturn(Optional.of(testProductStock));
        when(productStockRepository.save(any(ProductStock.class))).thenAnswer(invocation -> {
            ProductStock savedStock = invocation.getArgument(0);
            testProductStock.setQuantity(savedStock.getQuantity());
            return testProductStock;
        });

        ProductStock result = productStockUpdater.setStock(testProductId, 500);

        assertEquals(500, result.getQuantity());
        verify(productStockRepository).save(testProductStock);
    }

    @Test
    void increaseStock_Successful() {
        when(productStockRepository.findByProductId(testProductId)).thenReturn(Optional.of(testProductStock));

        productStockUpdater.increaseStock(testProductId, 20);

        assertEquals(70, testProductStock.getQuantity());
        verify(productStockRepository).save(testProductStock);
    }

    @Test
    void decreaseStock_Successful() {
        when(productStockRepository.findByProductId(testProductId)).thenReturn(Optional.of(testProductStock));

        productStockUpdater.decreaseStock(testProductId, 20);

        assertEquals(30, testProductStock.getQuantity());
        verify(productStockRepository).save(testProductStock);
    }

}
