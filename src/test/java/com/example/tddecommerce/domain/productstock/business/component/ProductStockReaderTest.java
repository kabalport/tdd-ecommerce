package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductStockReaderTest {

    private IProductStockRepository productStockRepository;
    private IProductRepository productRepository;
    private ProductStockReader productStockReader;

    @BeforeEach
    void setUp() {
        // Mock 객체 생성
        productStockRepository = Mockito.mock(IProductStockRepository.class);
        productRepository = Mockito.mock(IProductRepository.class);

        // ProductStockReader 인스턴스 생성 및 Mock 주입
        productStockReader = new ProductStockReader(productStockRepository, productRepository);
    }

    @Test
    void getProductStock_WhenStockExists() {
        Long productId = 1L;
        Product product = new Product();  // 상품 객체 생성
        ProductStock expectedStock = new ProductStock(product.getId(), 10);

        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(product));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(expectedStock));

        ProductStock result = productStockReader.getProductStock(productId);

        assertNotNull(result);
        assertEquals(expectedStock, result);
        verify(productStockRepository).findByProductId(productId);
    }
    @Test
    void getProductStock_WhenStockDoesNotExist_ReturnsDefaultStock() {
        Long productId = 2L;
        Product product = new Product();
        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(product));
        when(productStockRepository.findByProductId(productId)).thenReturn(Optional.empty());

        ProductStock result = productStockReader.getProductStock(productId);

        assertNotNull(result);
        assertEquals(0, result.getQuantity());
        verify(productRepository).findByProductId(productId);
        verify(productStockRepository).findByProductId(productId);
    }

}
