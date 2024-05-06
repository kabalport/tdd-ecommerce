package com.example.tddecommerce.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CreateOrderRequestFixture {
    Order expectedOrder = new Order(1L, "2023-08-01", "Created", new BigDecimal(10000));
    Order expectedOrderPayRequest = new Order(2L, "2024-05-01", "PayRequest", new BigDecimal(120000));
    public static CreateOrderRequest createOrderRequest() {
        Long givenCustomerId = 1L;
        List<OrderItemDto> orderItemDto = List.of(
                new OrderItemDto(1L, 3, new BigDecimal(10000))
                , new OrderItemDto(2L, 32, new BigDecimal(120000))
        );
        return new CreateOrderRequest(givenCustomerId, orderItemDto);
    }

    public static CreateOrderRequest createOrderRequest(Long customerId) {
        List<OrderItemDto> orderItemDto = List.of(
                new OrderItemDto(1L, 3, new BigDecimal(10000))
                , new OrderItemDto(2L, 32, new BigDecimal(120000))
        );
        return new CreateOrderRequest(customerId, orderItemDto);
    }

    public static CreateOrderRequest createOrderRequest(Long customerId, int numberOfItems) {
        List<OrderItemDto> items = IntStream.range(0, numberOfItems)
                .mapToObj(i -> new OrderItemDto(i + 1L, (i + 1) * 2, BigDecimal.valueOf(10000L + i * 1000)))
                .collect(Collectors.toList());
        return new CreateOrderRequest(customerId, items);
    }
}
