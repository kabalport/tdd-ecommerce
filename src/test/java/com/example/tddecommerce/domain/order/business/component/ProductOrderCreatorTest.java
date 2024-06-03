package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.user.business.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.verify;

class ProductOrderCreatorTest {

    private IProductOrderRepository iProductOrderRepository;

    private ProductOrderCreator productOrderCreator;

    @BeforeEach
    void setUp() {
        iProductOrderRepository = Mockito.mock(IProductOrderRepository.class);
        productOrderCreator = new ProductOrderCreator(iProductOrderRepository);
    }

    @Test
    void testSaveOrder() {
        // Create a User instance using constructor
        User user = new User("홍길동", "gildong@gmail.com");

        // Create a Product instance using constructor
        Product product = new Product("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE);

        // Create a ProductOrderItem instance using constructor
        ProductOrderItem orderItem = new ProductOrderItem(product, 2, BigDecimal.valueOf(200));

        // Create a ProductOrder instance using constructor
        ProductOrder order = new ProductOrder(user.getUserId(), LocalDate.now(), ProductOrderStatus.PENDING, Collections.singletonList(orderItem));
//        Mockito.when(iProductOrderRepository.save(Mockito.any(ProductOrder.class))).thenReturn(order);

        // Call saveOrder
        productOrderCreator.saveOrder(order);

        // Verify the save method was called
        verify(iProductOrderRepository).save(order);
    }
}
