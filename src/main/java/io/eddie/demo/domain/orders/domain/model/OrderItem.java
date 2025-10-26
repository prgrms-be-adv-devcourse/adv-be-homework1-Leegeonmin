package io.eddie.demo.domain.orders.domain.model;

import java.time.LocalDateTime;

public class OrderItem {

    private String code;

    private Orders orders;

    private String productCode;

    private String productName;

    private Long productPrice;

    private Integer quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public OrderItem(Orders orders, String productCode, String productName, Long productPrice, Integer quantity) {
        this.orders = orders;
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public Orders getOrder() {
        return orders;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setOrder(Orders orders) {
        this.orders = orders;
    }
}
