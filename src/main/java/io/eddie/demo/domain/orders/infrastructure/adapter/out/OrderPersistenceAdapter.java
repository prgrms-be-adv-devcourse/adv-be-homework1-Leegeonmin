package io.eddie.demo.domain.orders.infrastructure.adapter.out;

import io.eddie.demo.domain.orders.application.port.out.OrderPersistencePort;
import io.eddie.demo.domain.orders.domain.model.Orders;
import io.eddie.demo.domain.orders.infrastructure.mapper.OrderMapper;
import io.eddie.demo.domain.orders.infrastructure.model.entity.OrdersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {
    private final OrderJpaRepository jpaRepository;

    @Override
    public Orders save(Orders order) {
        return OrderMapper.toDomain(
                jpaRepository.save(
                        OrderMapper.toEntity(order)
                )
        );
    }

    @Override
    public Optional<Orders> findByCode(String code) {

        return Optional.empty();
    }

    @Override
    public Optional<Orders> findByAccountCodeAndCode(String accountCode, String code) {

        Optional<OrdersEntity> byAccountCodeAndCode = jpaRepository.findByAccountCodeAndCode(accountCode, code);

        return byAccountCodeAndCode.map(OrderMapper::toDomain);
    }

    @Override
    public Page<Orders> findAllByAccountCode(String accountCode, Pageable pageable) {
        Page<OrdersEntity> OrdersEntityList = jpaRepository.findAllByAccountCode(accountCode, pageable);
        return OrdersEntityList.map(OrderMapper::toDomain);
    }
}
