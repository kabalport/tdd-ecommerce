package com.example.tddecommerce.domain.productstock.application;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockServiceTest extends IntegrationTest {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductStockRepository productStockRepository;

    private Product product;
    private ProductStock productStock;

    @BeforeEach
    void setUp() {
        // 유저 생성 및 저장
        product = new Product("Test Product", BigDecimal.valueOf(100), "Test Description", DiscountPolicy.NONE);
        productRepository.save(product);

        // 재고 생성 및 저장
        productStock = new ProductStock(product, 50);
        productStockRepository.save(productStock);
    }
    @Test
    @Transactional
    void createProductStock_success(){
        Product addProduct = new Product("add Test Product", BigDecimal.valueOf(100), "add Test Description", DiscountPolicy.NONE);
        Product addProductResult = productRepository.save(addProduct);

        ProductStock addProductStock = new ProductStock(addProductResult, 50);
        productStockService.createProductStock(addProductStock);

        assertNotNull(addProductStock);
        assertEquals(50, addProductStock.getQuantity());
    }

    @Test
    @Transactional
    void getProductStock_success() {
        ProductStock retrievedStock = productStockService.getProductStock(product);
        assertNotNull(retrievedStock);
        assertEquals(50, retrievedStock.getQuantity());
    }

    @Test
    @Transactional
    void increaseProductStock_success() {
        ProductStock increasedStock = productStockService.increaseProductStock(productStock, 20);
        assertEquals(70, increasedStock.getQuantity());
    }

    @Test
    @Transactional
    void decreaseProductStock_success() {
        ProductStock decreasedStock = productStockService.decreaseProductStock(productStock, 10);
        assertEquals(40, decreasedStock.getQuantity());
    }

    @Test
    @Transactional
    void decreaseProductStock_insufficientStock_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            productStockService.decreaseProductStock(productStock, 60);
        });
    }
}
