package com.example.tddecommerce.domain.productstock.application;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class ProductStockServiceTest extends IntegrationTest {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IProductStockRepository productStockRepository;

    @Autowired
    private ProductStockService productStockService;

    private Product testProduct;
    private ProductStock testProductStock;

    @BeforeEach
    void setUp() {
        testProduct = new Product("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE);
        testProduct = productRepository.save(testProduct); // Save product to generate ID
        testProductStock = new ProductStock(testProduct.getId(), 50);
        testProductStock = productStockRepository.save(testProductStock); // Save initial stock
    }

    @Test
    void getProductStock_Successful() {
        ProductStock result = productStockService.getProductStock(testProduct.getId());

        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getProductId());
        assertEquals(testProductStock.getQuantity(), result.getQuantity());
    }

    @Test
    void increaseProductStock_Successful() {
        ProductStock result = productStockService.increaseProductStock(testProductStock, 20);

        assertNotNull(result);
        assertEquals(70, result.getQuantity());

        // Verify directly from the repository
        ProductStock updatedStock = productStockRepository.findByProductId(testProduct.getId()).orElseThrow();
        assertEquals(70, updatedStock.getQuantity());
    }

    @Test
    void decreaseProductStock_Successful() {
        ProductStock result = productStockService.decreaseProductStock(testProductStock, 20);

        assertNotNull(result);
        assertEquals(30, result.getQuantity());

        // Verify directly from the repository
        ProductStock updatedStock = productStockRepository.findByProductId(testProduct.getId()).orElseThrow();
        assertEquals(30, updatedStock.getQuantity());
    }

    @Test
    void validateAndDecreaseStock_Successful() {
//        ProductOrderItem orderItem = new ProductOrderItem(testProduct, 20, testProduct.getPrice());
//        productStockService.validateAndDecreaseStock(List.of(orderItem));

//        ProductStock updatedStock = productStockRepository.findByProductId(testProduct.getId()).orElseThrow();
//        assertEquals(30, updatedStock.getQuantity());
    }
}
