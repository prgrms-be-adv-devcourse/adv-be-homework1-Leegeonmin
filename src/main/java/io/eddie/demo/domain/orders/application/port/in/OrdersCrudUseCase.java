package io.eddie.demo.domain.orders.application.port.in;

import io.eddie.demo.domain.orders.domain.model.Orders;
import io.eddie.demo.domain.orders.infrastructure.model.vo.CreateOrderRequest;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface OrdersCrudUseCase {
    Orders createOrder(String accountCode, CreateOrderRequest request);

    Orders getOrder(String accountCode, String orderCode);

    Orders getOrderByCode(String code);

    Orders completeOrder(String accountCode, String orderCode);
    Orders cancelOrder(String accountCode, String orderCode);

    Page<Orders> getOrders(String accountCode, Pageable pageable);
}
