package io.eddie.demo.domain.orders.application.service;

import io.eddie.demo.domain.carts.application.service.CartApplication;
import io.eddie.demo.domain.carts.domain.model.CartItem;
import io.eddie.demo.domain.orders.application.port.in.OrdersCrudUseCase;
import io.eddie.demo.domain.orders.application.port.out.OrderItemPersistencePort;
import io.eddie.demo.domain.orders.application.port.out.OrderPersistencePort;
import io.eddie.demo.domain.orders.domain.model.OrderItem;
import io.eddie.demo.domain.orders.domain.model.Orders;
import io.eddie.demo.domain.orders.domain.vo.OrderState;
import io.eddie.demo.domain.orders.infrastructure.model.vo.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderApplication implements OrdersCrudUseCase {
    private final OrderItemPersistencePort orderItemPersistencePort;
    private final OrderPersistencePort orderPersistencePort;
    private final CartApplication cartService;

    @Override
    @Transactional
    public Orders createOrder(String accountCode, CreateOrderRequest request) {
        Orders order = new Orders(accountCode);

        List<CartItem> targetItems = cartService.getItemsByCodes(request.cartItemCodes());

        if ( targetItems.isEmpty() )
            throw new IllegalStateException("선택된 아이템이 없습니다");

        List<OrderItem> orderItems = targetItems.stream()
                .map(i -> {
                    OrderItem item = new OrderItem(
                            order,
                            i.getProductCode(),
                            i.getProductName(),
                            i.getProductPrice(),
                            i.getQuantity()
                    );
                    order.addItem(item);
                    return item;
                })
                .toList();

        orderPersistencePort.save(order);

        orderItemPersistencePort.saveAll(orderItems);
        cartService.deleteItemsByCodes(request.cartItemCodes());

        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Orders getOrder(String accountCode, String orderCode) {
        return orderPersistencePort.findByAccountCodeAndCode(accountCode, orderCode)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public Orders getOrderByCode(String code) {

        return orderPersistencePort.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public Orders completeOrder(String accountCode, String orderCode) {

        Orders targetOrder = getOrder(accountCode, orderCode);
        targetOrder.complete();

        return targetOrder;
    }

    @Override
    @Transactional
    public Orders cancelOrder(String accountCode, String orderCode) {

        Orders order = getOrder(accountCode, orderCode);

        if ( order.getOrderState() == OrderState.CANCELLED ) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }
        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Orders> getOrders(String accountCode, Pageable pageable) {

        return orderPersistencePort.findAllByAccountCode(accountCode, pageable);    }
}
