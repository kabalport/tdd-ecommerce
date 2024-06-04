package com.example.tddecommerce.domain.payment.business;

import com.example.tddecommerce.domain.order.business.component.ProductOrderCreator;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.payment.business.component.PaymentCreator;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import com.example.tddecommerce.domain.payment.business.model.PaymentStatus;
import com.example.tddecommerce.domain.payment.business.repository.IPaymentRepository;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentServiceTest extends IntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentCreator paymentCreator;

    @Autowired
    private ProductOrderCreator productOrderCreator;

    @Autowired
    private IPaymentRepository paymentRepository;

    private ProductOrder productOrder;

    @BeforeEach
    void setUp() {
        // 필요한 객체를 설정합니다.
        productOrder = ProductOrder.builder()
                .userId(1L)
                .orderDate(LocalDate.now())
                .orderStatus(ProductOrderStatus.PENDING)
//                .totalAmount(BigDecimal.valueOf(1000))
//                .amountToBePaid(BigDecimal.valueOf(800))
                .build();
        productOrderCreator.saveOrder(productOrder);
    }

    @Test
    void testProcessPaymentSuccess() {
        // Given

        // When
//        paymentService.processPayment(productOrder);

        // Then
//        Optional<Payment> paymentOptional = paymentRepository.findByOrder(productOrder);
//        assertEquals(true, paymentOptional.isPresent());
//        assertEquals(PaymentStatus.SUCCESS, paymentOptional.get().getStatus());
//        assertEquals(ProductOrderStatus.PAID, productOrder.getOrderStatus());
    }

    @Test
    void testProcessPaymentFailure() {
        // Given
        // 결제가 실패하도록 PaymentCreator 설정 (임의로 실패를 발생시키는 로직 추가 필요)
        PaymentCreator failingPaymentCreator = new PaymentCreator(paymentRepository) {
            @Override
            public boolean createPayment(ProductOrder order, BigDecimal amount, String paymentMethod) {
                Payment payment = Payment.builder()
                        .order(order)
                        .amount(amount)
                        .paymentDateTime(LocalDateTime.now())
                        .paymentMethod(paymentMethod)
                        .status(PaymentStatus.FAILED)  // 실패로 설정
                        .build();
                paymentRepository.save(payment);
                return false;
            }
        };

        paymentService = new PaymentService(failingPaymentCreator, productOrderCreator);

        // When & Then
        assertThrows(ProductException.class, () -> {
            paymentService.processPayment(productOrder);
        });
        assertEquals(ProductOrderStatus.PENDING, productOrder.getOrderStatus());
    }
}
