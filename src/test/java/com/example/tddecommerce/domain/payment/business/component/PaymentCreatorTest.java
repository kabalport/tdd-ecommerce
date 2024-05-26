package com.example.tddecommerce.domain.payment.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import com.example.tddecommerce.domain.payment.business.model.PaymentStatus;
import com.example.tddecommerce.domain.payment.business.repository.IPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentCreatorTest {

    private PaymentCreator paymentCreator;
    private IPaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = Mockito.mock(IPaymentRepository.class);
        paymentCreator = new PaymentCreator(paymentRepository);
    }

    @Test
    void createPayment_successful() {
        ProductOrder productOrder = new ProductOrder();
        BigDecimal amount = BigDecimal.valueOf(200);
        String paymentMethod = "CREDIT_CARD";

        boolean result = paymentCreator.createPayment(productOrder, amount, paymentMethod);

        // Assert the payment creation result
        assertTrue(result);

        // Capture the saved payment object
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());
        Payment savedPayment = paymentCaptor.getValue();

        // Assert the saved payment details
        assertNotNull(savedPayment);
        assertEquals(amount, savedPayment.getAmount());
        assertEquals(paymentMethod, savedPayment.getPaymentMethod());
        assertEquals(PaymentStatus.SUCCESS, savedPayment.getStatus());
        assertNotNull(savedPayment.getPaymentDateTime());
    }
}
