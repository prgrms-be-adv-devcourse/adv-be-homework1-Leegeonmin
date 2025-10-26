package io.eddie.demo.domain.orders.infrastructure.adapter.out;

import io.eddie.demo.domain.orders.application.port.out.OrderItemPersistencePort;
import io.eddie.demo.domain.orders.domain.model.OrderItem;
import io.eddie.demo.domain.orders.infrastructure.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemPersistenceAdapter implements OrderItemPersistencePort {
    private final OrderItemJpaRepository jpaRepository;

    public void saveAll(List<OrderItem> orderItems) {
        jpaRepository.saveAll(orderItems.stream().map( x ->OrderMapper.toEntity(x, OrderMapper.toEntity(x.getOrder()))).toList());
    }
}
