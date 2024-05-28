package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.component.ProductOrderValidate;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.PaymentService;
import com.example.tddecommerce.domain.product.application.ProductService;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // 재고 확인
        ProductStock productStock= productStockService.getProductStock(null);

        // 주문 생성
        ProductOrder order = productOrderService.createOrder(null, null);

        // 결제 처리
        paymentService.processPayment(order);

        // 재고 업데이트
        productStockService.decreaseProductStock(productStock,1);

        // 이메일 발송
        emailService.sendOrderConfirmationEmail(null,null);

        // 포인트 적립 (옵션)
    }
}
