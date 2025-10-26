package io.eddie.demo.domain.orders.infrastructure.adapter.out;

import io.eddie.demo.domain.orders.infrastructure.model.entity.OrdersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrdersEntity, Long> {
    Optional<OrdersEntity> findByAccountCodeAndCode(String accountCode, String code);
    Page<OrdersEntity> findAllByAccountCode(String accountCode, Pageable pageable);
}
