package io.eddie.demo.domain.orders.infrastructure.model.dto;

public record OrderItemDescription(
        String productCode,
        String productName,
        Long productPrice,
        Integer quantity) {
}