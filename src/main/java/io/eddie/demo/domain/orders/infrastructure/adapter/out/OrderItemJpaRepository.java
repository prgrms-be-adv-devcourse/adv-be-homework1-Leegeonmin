package io.eddie.demo.domain.orders.infrastructure.adapter.out;

import io.eddie.demo.domain.orders.infrastructure.model.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
}
