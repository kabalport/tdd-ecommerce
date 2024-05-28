package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.component.ProductOrderValidate;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.PaymentService;
import com.example.tddecommerce.domain.product.application.ProductService;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductOrderUseCase {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPointService userPointService;
    @Autowired
    private ProductStockService productStockService;

    public void execute(Long userId, List<ProductOrderDetail> productOrderDetails) {
        // 유저조회
        User user = userService.getUserById(userId);

        // 주문 생성 및 재고 확인/업데이트
        ProductOrder order = productOrderService.createOrder(userId, productOrderDetails);

        // 결제 처리
        paymentService.processPayment(order);

        // 이메일 발송
        emailService.sendOrderConfirmationEmail(user.getEmail(), order);

        // 포인트 적립 (옵션)
       // userPointService.addPoints(userId, order.getTotalAmount().multiply(BigDecimal.valueOf(0.1)));
    }
}
