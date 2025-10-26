package io.eddie.demo.domain.orders.domain.model;

import io.eddie.demo.domain.orders.domain.vo.OrderState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Orders {


    private String code;

    private String accountCode;

    private OrderState orderState = OrderState.CREATED;


    private List<OrderItem> orderItems = new ArrayList<>();

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Orders(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public Orders(String accountCode,
                  String code,
                  LocalDateTime createdAt,
                  LocalDateTime updatedAt,
                  List<OrderItem> orderItems,
                  OrderState orderState
    ) {
        this.accountCode = accountCode;
        this.code = code;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderItems = orderItems;
        this.orderState = orderState;
    }
    public Long getTotalPrice() {
        return orderItems.stream()
                .mapToLong(i -> i.getProductPrice() * i.getQuantity())
                .sum();
    }

    public String getCode() {
        return code;
    }

    public void addItem(OrderItem item) {
        this.orderItems.add(item);
        item.setOrder(this);
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public boolean canPay() {
        return this.orderState == OrderState.CREATED;
    }

    public void cancel() {
        this.orderState = OrderState.CANCELLED;
    }

    public void complete() {
        this.orderState = OrderState.PAID;
    }

}
