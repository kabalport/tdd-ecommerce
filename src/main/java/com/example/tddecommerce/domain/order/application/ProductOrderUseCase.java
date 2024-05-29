package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.PaymentService;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProductOrderUseCase {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    /**
     * 제품 주문을 시작한다
     * 서비스 앞에 파사드를 두어 파사드에서 서비스들을 관리한다
     * @param userId 유저 ID
     * @param productOrderDetails 주문 세부 정보 목록
     */
    @Transactional
    public void execute(Long userId, List<ProductOrderDetail> productOrderDetails) {
        try {
            // 유저 검증
            User user = userService.getUserById(userId);

            // 주문 생성
            ProductOrder order = productOrderService.createOrder(user.getUserId(), productOrderDetails, BigDecimal.ZERO);

            // 결제 처리
            paymentService.processPayment(order);

            // 이메일 발송
            emailService.sendOrderConfirmationEmail(user.getEmail(), order);

            log.info("해당주문완료-유저: {}", userId);

        } catch (Exception e) {
            log.error("주문에러 유저아이디: {}", userId, e);
            throw new RuntimeException("주문실패 보상트랜잭션 발동", e);
        }
    }
}
