package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import com.example.tddecommerce.domain.user.business.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class ProductOrderCreatorTest {

    @Mock
    private IProductOrderRepository productOrderRepository;

    @InjectMocks
    private ProductOrderCreator productOrderCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() {
        // Given
        User user = new User("John Doe", "john.doe@example.com");
        List<ProductOrderItem> items = Collections.emptyList();
        ProductOrder productOrder = ProductOrder.createProductOrder(user.getUserId(), LocalDate.now(), ProductOrderStatus.PENDING, items);

        // When
        productOrderCreator.saveOrder(productOrder);

        // Then
        ArgumentCaptor<ProductOrder> orderCaptor = ArgumentCaptor.forClass(ProductOrder.class);
        verify(productOrderRepository).save(orderCaptor.capture());
        ProductOrder capturedOrder = orderCaptor.getValue();
//        assertEquals(productOrder.getUserId().getName(), capturedOrder..getName());
        assertEquals(productOrder.getOrderDate(), capturedOrder.getOrderDate());
        assertEquals(productOrder.getStatus(), capturedOrder.getStatus());
    }
}

