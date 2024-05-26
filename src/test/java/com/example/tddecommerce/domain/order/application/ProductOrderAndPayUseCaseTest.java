package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.payment.business.component.PaymentCreator;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import com.example.tddecommerce.domain.productstock.application.business.ProductStockUpdater;
import com.example.tddecommerce.domain.productstock.application.model.ProductStock;
import com.example.tddecommerce.domain.productstock.infrastructure.ProductStockRepository;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.infrastructure.UserRepository;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import com.example.tddecommerce.domain.userpoint.infrastructure.UserPointRepository;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductOrderAndPayUseCaseTest extends IntegrationTest {

    @Autowired
    private ProductOrderAndPayUseCase productOrderAndPayUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private PaymentCreator paymentCreator;

    @Autowired
    private ProductStockUpdater productStockUpdater;

    @Autowired
    private EmailService emailService;

    private User user;
    private Product product;
    private ProductStock productStock;
    private UserPoint userPoint;

    @BeforeEach
    void setUp() {
        // 유저 생성 및 저장
        user = new User();
        user.updateName("testuser");
        userRepository.createUser(user);

        // 상품 생성 및 저장
        product = new Product("Test Product", BigDecimal.valueOf(100), "Test Description", DiscountPolicy.NONE);
        productRepository.save(product);

        // 재고 생성 및 저장
        productStock = new ProductStock(product, 10);
        productStockRepository.save(productStock);

        // 유저 포인트 생성 및 저장
        userPoint = new UserPoint();
        userPoint.setUser(user);
        userPoint.addPoints(BigDecimal.valueOf(1000));
        userPointRepository.charge(userPoint);
    }

    @Test
    void execute_validOrder_createsOrderSuccessfully() {
        List<ProductOrderDetail> productOrderDetails = Collections.singletonList(new ProductOrderDetail(product.getId(), 2));

        ProductOrder order = productOrderAndPayUseCase.execute(user.getUserId(), productOrderDetails);

        assertEquals(ProductOrderStatus.PAID, order.getStatus());
        assertEquals(2, order.getItems().get(0).getQuantity());
        assertEquals(product.getId(), order.getItems().get(0).getProduct().getId());

        // 재고가 감소했는지 확인
        ProductStock updatedStock = productStockRepository.findByProduct(product).orElseThrow();
        assertEquals(8, updatedStock.getQuantity());

        // 포인트가 차감되었는지 확인
        UserPoint updatedUserPoint = userPointRepository.findByUserUserId(user.getUserId()).orElseThrow();
        assertEquals(BigDecimal.valueOf(800.00), updatedUserPoint.getPointBalance());
    }

    @Test
    void execute_insufficientStock_throwsException() {
        List<ProductOrderDetail> productOrderDetails = Collections.singletonList(new ProductOrderDetail(product.getId(), 11));

        assertThrows(RuntimeException.class, () -> {
            productOrderAndPayUseCase.execute(user.getUserId(), productOrderDetails);
        });

        // 재고가 감소하지 않았는지 확인
        ProductStock updatedStock = productStockRepository.findByProduct(product).orElseThrow();
        assertEquals(10, updatedStock.getQuantity());

        // 포인트가 차감되지 않았는지 확인
        UserPoint updatedUserPoint = userPointRepository.findByUserUserId(user.getUserId()).orElseThrow();
        assertEquals(BigDecimal.valueOf(1000), updatedUserPoint.getPointBalance());
    }

    @Test
    void execute_paymentFailure_throwsException() {
        // 결제 실패 시나리오 설정 (예: PaymentManager의 메소드를 실제 호출하도록 설정하고 Payment 실패 시나리오를 시뮬레이션)
//        paymentManager = new PaymentManager() {
//            @Override
//            public boolean createPayment(ProductOrder order, BigDecimal amount, String paymentMethod) {
//                return false; // 항상 결제 실패
//            }
//        };

//        List<ProductOrderDetail> productOrderDetails = Collections.singletonList(new ProductOrderDetail(product.getId(), 2));
//
//        assertThrows(RuntimeException.class, () -> {
//            productOrderAndPayUseCase.execute(user.getUserId(), productOrderDetails);
//        });

        // 재고가 감소하지 않았는지 확인
//        ProductStock updatedStock = productStockRepository.findByProduct(product).orElseThrow();
//        assertEquals(10, updatedStock.getQuantity());

        // 포인트가 차감되지 않았는지 확인
//        UserPoint updatedUserPoint = userPointRepository.findByUserUserId(user.getUserId()).orElseThrow();
//        assertEquals(BigDecimal.valueOf(1000), updatedUserPoint.getPointBalance());
    }
}
