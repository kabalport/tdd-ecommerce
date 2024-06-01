package com.example.tddecommerce.domain.order.application.service;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.component.ProductOrderItemCreator;
import com.example.tddecommerce.domain.order.business.component.ProductOrderCreator;
import com.example.tddecommerce.domain.order.business.component.TotalAmountCalculator;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.product.business.component.ProductCreator;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductOrderServiceTest extends IntegrationTest {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductOrderCreator productOrderCreator;

    @Autowired
    private ProductOrderItemCreator productOrderItemCreator;

    @Autowired
    private TotalAmountCalculator totalAmountCalculator;

    @Autowired
    private ProductCreator productCreator;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product("Product 1", BigDecimal.valueOf(100), "Description 1", DiscountPolicy.NONE);
        product2 = new Product("Product 2", BigDecimal.valueOf(200), "Description 2", DiscountPolicy.NONE);
        productCreator.saveAllProducts(Arrays.asList(product1, product2));
    }

    @Test
    void testCreateOrder() {
        // Given
        ProductOrderDetail detail1 = new ProductOrderDetail(product1.getId(), 2);
        ProductOrderDetail detail2 = new ProductOrderDetail(product2.getId(), 3);

        // Prepare order items using ProductOrderItemCreator
        List<ProductOrderItem> items = productOrderItemCreator.prepareOrderItems(Arrays.asList(detail1, detail2));

        // Calculate total amount using TotalAmountCalculator
        BigDecimal totalAmount = totalAmountCalculator.calculateTotalAmount(items);

        // When
        ProductOrder order = productOrderService.createOrder(1L, items, totalAmount);

        // Then
        assertNotNull(order);
        assertEquals(1L, order.getUserId());
        assertEquals(LocalDate.now(), order.getOrderDate());
        assertEquals(ProductOrderStatus.PENDING, order.getStatus());
        assertEquals(0, totalAmount.compareTo(order.getTotalAmount())); // BigDecimal 비교

        List<ProductOrderItem> orderItems = order.getItems();
        assertEquals(2, orderItems.size());

        ProductOrderItem item1 = orderItems.get(0);
        assertEquals(product1.getId(), item1.getProduct().getId());
        assertEquals(2, item1.getQuantity());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(item1.getPrice())); // BigDecimal 비교

        ProductOrderItem item2 = orderItems.get(1);
        assertEquals(product2.getId(), item2.getProduct().getId());
        assertEquals(3, item2.getQuantity());
        assertEquals(0, BigDecimal.valueOf(600).compareTo(item2.getPrice())); // BigDecimal 비교
    }

    @Test
    void testPrepareOrderItems() {
        // Given
        ProductOrderDetail detail1 = new ProductOrderDetail(product1.getId(), 2);
        ProductOrderDetail detail2 = new ProductOrderDetail(product2.getId(), 3);

        // When
        List<ProductOrderItem> items = productOrderService.prepareOrderItems(Arrays.asList(detail1, detail2));

        // Then
        assertEquals(2, items.size());

        ProductOrderItem item1 = items.get(0);
        assertEquals(product1.getId(), item1.getProduct().getId());
        assertEquals(2, item1.getQuantity());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(item1.getPrice())); // BigDecimal 비교

        ProductOrderItem item2 = items.get(1);
        assertEquals(product2.getId(), item2.getProduct().getId());
        assertEquals(3, item2.getQuantity());
        assertEquals(0, BigDecimal.valueOf(600).compareTo(item2.getPrice())); // BigDecimal 비교
    }

    @Test
    void testPrepareAmountToBePaid() {
        // Given
        ProductOrderDetail detail1 = new ProductOrderDetail(product1.getId(), 2);
        ProductOrderDetail detail2 = new ProductOrderDetail(product2.getId(), 3);
        List<ProductOrderItem> items = productOrderService.prepareOrderItems(Arrays.asList(detail1, detail2));

        // When
        BigDecimal totalAmount = productOrderService.prepareAmountToBePaid(items);

        // Then
        assertEquals(0, BigDecimal.valueOf(800).compareTo(totalAmount)); // BigDecimal 비교
    }
}