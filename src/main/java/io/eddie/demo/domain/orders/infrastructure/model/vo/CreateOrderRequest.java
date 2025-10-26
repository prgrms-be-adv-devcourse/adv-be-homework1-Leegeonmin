package io.eddie.demo.domain.orders.infrastructure.model.vo;

import java.util.List;

public record CreateOrderRequest(
        List<String> cartItemCodes) {
}
