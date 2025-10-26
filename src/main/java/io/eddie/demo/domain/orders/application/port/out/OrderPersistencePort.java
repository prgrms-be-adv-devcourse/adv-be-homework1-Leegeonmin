package io.eddie.demo.domain.orders.application.port.out;

import io.eddie.demo.domain.orders.domain.model.Orders;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;

public interface OrderPersistencePort {
    Orders save(Orders order);
    Optional<Orders> findByCode(String code);

    Optional<Orders> findByAccountCodeAndCode(String accountCode, String code);

    Page<Orders> findAllByAccountCode(String accountCode, Pageable pageable);
}
