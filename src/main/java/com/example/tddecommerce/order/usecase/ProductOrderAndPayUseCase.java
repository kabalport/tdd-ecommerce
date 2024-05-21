package com.example.tddecommerce.order.usecase;


import com.example.tddecommerce.order.business.component.ProductOrderManager;
import com.example.tddecommerce.order.business.component.ProductOrderValidate;
import com.example.tddecommerce.order.business.model.ProductOrder;
import com.example.tddecommerce.order.business.model.ProductOrderItem;
import com.example.tddecommerce.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.order.controller.ProductOrderDTO;
import com.example.tddecommerce.product.application.business.ProductReader;
import com.example.tddecommerce.product.domain.model.Product;
import com.example.tddecommerce.user.business.component.UserReader;
import com.example.tddecommerce.user.business.domain.User;
import com.example.tddecommerce.userpoint.business.component.UserPointReader;
import com.example.tddecommerce.userpoint.business.component.UserPointValidator;
import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductOrderAndPayUseCase {
    private final ProductOrderValidate productOrderValidate;
    private final UserPointValidator userPointValidator;
    private final UserReader memberReader;
    private final UserPointReader userPointReader;
    private final ProductOrderManager productOrderManager;

    private final ProductReader productReader;

    public ProductOrder execute(ProductOrderDTO.Request request) {

        // 주문요청 유효성 검증
        productOrderValidate.validate(request);

        // 유저 조회
        User member = memberReader.read(request.getUserId());

        // 주문 항목 준비
        List<ProductOrderItem> items = request.getProducts().stream().map(detail -> {
            Optional<Product> getProduct = productReader.selectOne(detail.getProductId());
            Product product = getProduct.orElseThrow();
            // 재고감소 결제일어날때
            productStockManager.save();
            // 주문 항목 객체 생성
            return new ProductOrderItem(product, detail.getQuantity(), product.getPrice());
        }).collect(Collectors.toList());

        // 요청된 모든 상품에 대해 총 금액 계산
        int totalAmount = 0;
//        int totalAmount = items.stream()
//                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
//                .sum();


        // 사용자 포인트 정보 조회 및 검증
        UserPoint userPoint = userPointReader.read(member);
        userPointValidator.validatePurchase(userPoint, totalAmount);

        // 사용자 잔액 차감
        userPoint.purchase(totalAmount);

        // 주문 엔티티 생성
        ProductOrder order = ProductOrder.builder()
                .member(member)
                .orderDate(LocalDate.now())
                .status(ProductOrderStatus.PENDING)
                .items(items)
                .build();

        // 주문 저장 로직
        productOrderManager.saveOrder(order);
        paymentManager.createPayment(order, totalAmount, "결제수단");

        // 데이터 플랫폼으로 주문 정보 전송
        sendDataToDataPlatform(order);

        // 주문 정보 반환
        return order;
    }

    private void sendDataToDataPlatform(ProductOrder order) {
        // 결제 성공 시 데이터 플랫폼으로 주문 정보를 전송하는 로직 구현
        System.out.println("결제성공했고 데이터 플랫폼으로 주문 정보를 전송합니다.");
    }
}
