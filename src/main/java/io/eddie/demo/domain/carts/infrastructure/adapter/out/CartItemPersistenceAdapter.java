package io.eddie.demo.domain.carts.infrastructure.adapter.out;

import io.eddie.demo.domain.carts.application.port.out.CartItemPersistencePort;
import io.eddie.demo.domain.carts.domain.exception.CouldNotFindCartItemException;
import io.eddie.demo.domain.carts.domain.model.Cart;
import io.eddie.demo.domain.carts.domain.model.CartItem;
import io.eddie.demo.domain.carts.infrastructure.mapper.CartMapper;
import io.eddie.demo.domain.carts.infrastructure.model.persistence.CartItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CartItemPersistenceAdapter implements CartItemPersistencePort {
    private final CartItemJpaRepository cartItemJpaRepository;


    @Override
    public List<CartItem> findByCart(Cart cart) {
        return cartItemJpaRepository.findByCart(cart)
                .stream().map(CartMapper::toDomain).toList();
    }

    @Override
    public Optional<CartItem> findOwnCartItem(String accountCode, String cartItemCode) {
        return cartItemJpaRepository.findOwnCartItem(accountCode, cartItemCode)
                .map(CartMapper::toDomain);
    }

    @Override
    public List<CartItem> findAllByCodesIn(List<String> cartItemCodes) {
        return cartItemJpaRepository.findAllByCodesIn(cartItemCodes)
                .stream().map(CartMapper::toDomain).toList();
    }

    @Override
    public CartItem save(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemJpaRepository.findOwnCartItem(cartItem.getCart().getAacountCode(), cartItem.getCode())
                .orElseThrow(() -> new CouldNotFindCartItemException("장바구니 상품이 존재하지 않습니다"));
        CartMapper.applyToEntity(cartItem, cartItemEntity);
        return CartMapper.toDomain(cartItemJpaRepository.save(cartItemEntity));

    }
}
