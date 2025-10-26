package io.eddie.demo.domain.orders.infrastructure.model.dto;

import io.eddie.demo.domain.orders.domain.vo.OrderState;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDescription(
        String orderCode,
        OrderState orderStatus,
        List<OrderItemDescription> orderItems,
        LocalDateTime orderedAt,
        Long totalPrice
) {
}