package io.eddie.demo.domain.carts.application.port.out;

import io.eddie.demo.domain.carts.domain.model.Cart;

import java.util.Optional;

public interface CartPersistencePort {
    Optional<Cart> findByAccountCode(String accountCode);
    Cart Save(Cart cart);
}
