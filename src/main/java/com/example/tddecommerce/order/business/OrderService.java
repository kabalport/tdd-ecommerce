package com.example.tddecommerce.order.business;

import com.example.tddecommerce.order.api.OrderItemDto;
import com.example.tddecommerce.order.business.ICustomerRepository;
import com.example.tddecommerce.order.business.IOrderRepository;
import com.example.tddecommerce.order.business.IProductRepository;
import com.example.tddecommerce.order.business.IProductStockRepository;
import com.example.tddecommerce.order.api.CreateOrderRequest;
import com.example.tddecommerce.order.business.domain.*;
import com.example.tddecommerce.userPoint.business.UserPoint;
import com.example.tddecommerce.userPoint.infrastructure.IUserPointRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final ICustomerRepository customerRepository;
    private final IOrderRepository orderRepository;
    private final IUserPointRepository userPointRepository;
    private final IProductRepository productRepository;
    private final IProductStockRepository productStockRepository;

    public OrderService(ICustomerRepository customerRepository, IOrderRepository orderRepository, IUserPointRepository userPointRepository, IProductRepository productRepository, IProductStockRepository productStockRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.userPointRepository = userPointRepository;
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        // 주문요청 유효성검증
        createOrderRequestValidate(request);

        // 고객정보조회
        Customer customer = customerRepository.findByUserId(request.getUserId()).orElseThrow(() -> new RuntimeException("고객정보가 없습니다"));

        // 주문항목 준비 및 주문항목 객체 생성
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemDto itemDto : request.getOrderItems()) {
            Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(() -> new RuntimeException("상품 정보가 없습니다: " + itemDto.getProductId()));

            // 상품들 재고차감
            ProductStock stock = product.getStock();
            if (stock == null) {
                throw new RuntimeException("재고정보가 없습니다." + product.getName());
            }
            stock.decreaseStock(itemDto.getOrderQuantity()); // 재고 차감
            productStockRepository.save(stock); // 재고 정보 업데이트

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getOrderQuantity());
            orderItem.setPrice(itemDto.getOrderPrice());
            orderItems.add(orderItem);

            // 요청된 모든 상품에 대해 총 금액 계산
            BigDecimal itemTotal = itemDto.getOrderPrice().multiply(new BigDecimal(itemDto.getOrderQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        // 사용자 포인트정보조회 및 검증
        String userId = request.getUserId();
        UserPoint userPoint = userPointRepository.findByUserId(userId);
        userPointValidatorValidatePurchase(userPoint, totalAmount);

        // 사용자 잔액 차감
        userPoint.decrease(totalAmount);
        userPointRepository.save(userPoint);

        // 주문엔티티 생성
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date().toString());
        order.setOrderStatus("주문완료");
        order.setOrderItem(orderItems);  // 이제 orderItems 리스트를 설정합니다.
        order.setOrderTotalPrice(totalAmount);

        // 주문 저장 로직
        orderRepository.save(order);

        // 데이터 플랫폼으로 주문 정보 전송
        sendDataToDataPlatform(order);

        return order;
    }

    private void createOrderRequestValidate(CreateOrderRequest request) {

    }

    private void userPointValidatorValidatePurchase(UserPoint userPoint, BigDecimal totalAmount) {
        // 구매 금액은 0보다 커야 합니다.
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("구매 금액은 0보다 커야 합니다.");
        }
        // 잔액이 부족합니다.
        if (userPoint.getPoint().signum() < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }
    }


    private void sendDataToDataPlatform(Order order) {
        // 결제 성공 시 데이터 플랫폼으로 주문 정보를 전송하는 로직 구현
        System.out.println("결제성공했고 데이터 플랫폼으로 주문 정보를 전송합니다.");
    }
}
