package io.eddie.demo.domain.carts.infrastructure.adapter.out;

import io.eddie.demo.domain.carts.infrastructure.model.persistence.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByAccountCode(String accountCode);

}
