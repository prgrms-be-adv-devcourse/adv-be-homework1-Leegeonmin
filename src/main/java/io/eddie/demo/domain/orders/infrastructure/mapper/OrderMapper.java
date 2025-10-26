package io.eddie.demo.domain.orders.infrastructure.mapper;

import io.eddie.demo.domain.orders.domain.model.OrderItem;
import io.eddie.demo.domain.orders.domain.model.Orders;
import io.eddie.demo.domain.orders.infrastructure.model.dto.OrderDescription;
import io.eddie.demo.domain.orders.infrastructure.model.dto.OrderItemDescription;
import io.eddie.demo.domain.orders.infrastructure.model.entity.OrderItemEntity;
import io.eddie.demo.domain.orders.infrastructure.model.entity.OrdersEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static OrderDescription toOrderDescription(Orders order) {
        return new OrderDescription(
             order.getCode(),
             order.getOrderState(),
                order.getOrderItems().stream().map(OrderMapper::toOrderItemDescription).toList(),
                order.getCreatedAt(),
                order.getTotalPrice()
        );
    }

    public static OrderItemDescription toOrderItemDescription(OrderItem orderItem) {
        return new OrderItemDescription(
                orderItem.getProductCode(),
                orderItem.getProductName(),
                orderItem.getProductPrice(),
                orderItem.getQuantity()
        );
    }

    public static OrdersEntity toEntity(Orders order) {
        OrdersEntity ordersEntity = new OrdersEntity(order.getAccountCode());
        List<OrderItemEntity> orderItems = order.getOrderItems().stream().map(x->OrderMapper.toEntity(x, ordersEntity)).toList();
        ordersEntity.setOrderItems(orderItems);
        return ordersEntity;
    }

    public static OrderItemEntity toEntity(OrderItem orderItem, OrdersEntity order) {
        return OrderItemEntity.builder()
                .order(order)
                .productCode(orderItem.getProductCode())
                .productName(orderItem.getProductName())
                .productPrice(orderItem.getProductPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static Orders toDomain(OrdersEntity entity) {
        Orders order =  new Orders(
                entity.getAccountCode(),
                entity.getCode(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                new ArrayList<>(),
                entity.getOrderState()
        );

        entity.getOrderItems().forEach(itemEntity -> {
            OrderItem item = OrderMapper.toDomain(itemEntity);
            order.addItem(item);
        });
        return order;
    }

    public static OrderItem toDomain(OrderItemEntity entity){
        return new OrderItem(
                null,
                entity.getProductCode(),
                entity.getProductName(),
                entity.getProductPrice(),
                entity.getQuantity()
        );
    }
}
