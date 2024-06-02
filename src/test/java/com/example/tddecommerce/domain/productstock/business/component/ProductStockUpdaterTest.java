package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductStockUpdaterTest {


    private IProductStockRepository productStockRepository;
    private IProductRepository productRepository;
    private ProductStockUpdater productStockUpdater;

    Product testProduct;
    @BeforeEach
    void setUp() {
        productStockRepository = Mockito.mock(IProductStockRepository.class);
        productRepository = Mockito.mock(IProductRepository.class);
        productStockUpdater = new ProductStockUpdater(productStockRepository,productRepository);
        testProduct = new Product("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE);

    }
    @Test
    void setStock_Successful() {
        Product product = productRepository.save(testProduct);
        Long productId = product.getId();


        ProductStock stock = new ProductStock(testProduct, 50);  // Assuming product is initialized
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(stock));
        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(testProduct));

        ProductStock result = productStockUpdater.setStock(productId, 500);

        assertEquals(500, result.getQuantity());
        verify(productStockRepository).save(stock);
    }



    @Test
    void setStock_Failure_QuantityTooHigh() {
        Long productId = 1L;
        ProductStock stock = new ProductStock(null, 50);
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(stock));

        assertThrows(ProductException.class, () -> productStockUpdater.setStock(productId, 1001));

        assertEquals(50, stock.getQuantity());
        verify(productStockRepository, never()).save(stock);
    }

    @Test
    void setStock_Failure_QuantityTooLow() {
        Long productId = 1L;
        ProductStock stock = new ProductStock(null, 50);
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(stock));

        assertThrows(ProductException.class, () -> productStockUpdater.setStock(productId, -1));

        assertEquals(50, stock.getQuantity());
        verify(productStockRepository, never()).save(stock);
    }
    @Test
    void increaseStock_Successful() {
        Long productId = 1L;
        ProductStock stock = new ProductStock(null, 50);
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(stock));

        productStockUpdater.increaseStock(productId, 20);

        assertEquals(70, stock.getQuantity());
        verify(productStockRepository).save(stock);
    }

    @Test
    void decreaseStock_Successful() {
        Long productId = 1L;
        ProductStock stock = new ProductStock(null, 50);
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(stock));

        productStockUpdater.decreaseStock(productId, 20);

        assertEquals(30, stock.getQuantity());
        verify(productStockRepository).save(stock);
    }

    @Test
    void decreaseStock_Failure_NotEnoughStock() {
        Long productId = 1L;
        ProductStock stock = new ProductStock(null, 10);
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(stock));

        Exception exception = assertThrows(ProductException.class, () -> productStockUpdater.decreaseStock(productId, 20));

        assertEquals("재고는 0개 미만이 될 수 없습니다.", exception.getMessage());
        verify(productStockRepository, never()).save(stock);
    }
}
