package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductOrderUpdaterTest {

    private IProductOrderRepository productOrderRepository;
    private ProductOrderUpdater productOrderUpdater;

    @BeforeEach
    void setUp() {
        productOrderRepository = Mockito.mock(IProductOrderRepository.class);
        productOrderUpdater = new ProductOrderUpdater(productOrderRepository);
    }

    @Test
    void testUpdateOrderStatus() {
        Long orderId = 1L;
        ProductOrderStatus newStatus = ProductOrderStatus.CONFIRMED;

        // 기존 ProductOrder 인스턴스 생성
        ProductOrder order = new ProductOrder(orderId, 1L, LocalDate.now(), ProductOrderStatus.PENDING, Collections.emptyList(), BigDecimal.ZERO, BigDecimal.ZERO);

        // 레포지토리 findById 메서드 목업
        when(productOrderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // updateOrderStatus 호출
        productOrderUpdater.updateOrderStatus(orderId, newStatus);

        // 저장된 주문 캡처
        ArgumentCaptor<ProductOrder> orderCaptor = ArgumentCaptor.forClass(ProductOrder.class);
        verify(productOrderRepository).save(orderCaptor.capture());
        ProductOrder savedOrder = orderCaptor.getValue();

        // 주문 상태가 업데이트되었는지 확인
        assertEquals(newStatus, savedOrder.getOrderStatus());
    }

    @Test
    void testUpdateOrderStatus_OrderNotFound() {
        Long orderId = 1L;
        ProductOrderStatus newStatus = ProductOrderStatus.CONFIRMED;

        // 레포지토리 findById 메서드가 빈 값을 반환하도록 목업
        when(productOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        // updateOrderStatus 호출 및 예외 기대
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productOrderUpdater.updateOrderStatus(orderId, newStatus));

        // 예외 메시지 확인
        assertEquals("Order not found with id: " + orderId, exception.getMessage());
    }

    @Test
    void testUpdateOrder() {
        Long orderId = 1L;

        // 기존 ProductOrder 인스턴스 생성
        ProductOrder existingOrder = new ProductOrder(orderId, 1L, LocalDate.now(), ProductOrderStatus.PENDING, Collections.emptyList(), BigDecimal.ZERO, BigDecimal.ZERO);

        // 업데이트된 상세 정보가 포함된 새 ProductOrder 인스턴스 생성
        Product product = new Product("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE);
        ProductOrderItem orderItem = new ProductOrderItem(product.getId(), 2, BigDecimal.valueOf(200));
        ProductOrder updatedOrder = new ProductOrder(null, 1L, LocalDate.now(), ProductOrderStatus.CONFIRMED, Collections.singletonList(orderItem), BigDecimal.ZERO, BigDecimal.ZERO);

        // 레포지토리 findById 메서드 목업
        when(productOrderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        // updateOrder 호출
        productOrderUpdater.updateOrder(orderId, updatedOrder);

        // 저장된 주문 캡처
        ArgumentCaptor<ProductOrder> orderCaptor = ArgumentCaptor.forClass(ProductOrder.class);
        verify(productOrderRepository).save(orderCaptor.capture());
        ProductOrder savedOrder = orderCaptor.getValue();

        // 주문이 업데이트되었는지 확인
        assertEquals(ProductOrderStatus.CONFIRMED, savedOrder.getOrderStatus());
        assertEquals(1, savedOrder.getOrderItems().size());
//        assertEquals(product, savedOrder.getOrderItems().get(0).getProduct());
    }
}
