package io.eddie.demo.domain.carts.infrastructure.adapter.out;

import io.eddie.demo.domain.carts.application.port.out.CartPersistencePort;
import io.eddie.demo.domain.carts.domain.model.Cart;
import io.eddie.demo.domain.carts.infrastructure.mapper.CartMapper;
import io.eddie.demo.domain.carts.infrastructure.model.persistence.CartEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartPersistenceAdapter implements CartPersistencePort {
    private final CartJpaRepository cartRepository;

    @Override
    public Optional<Cart> findByAccountCode(String accountCode) {
        return cartRepository.findByAccountCode(accountCode)
                .map(CartMapper::toDomain);
    }

    @Override
    public Cart Save(Cart cart) {
        CartEntity entity = CartMapper.toEntity(cart);

        return CartMapper.toDomain(cartRepository.save(entity));
    }


}
