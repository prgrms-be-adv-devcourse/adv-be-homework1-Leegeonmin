package io.eddie.demo.domain.carts.infrastructure.adapter.out;

import io.eddie.demo.domain.carts.domain.model.Cart;
import io.eddie.demo.domain.carts.infrastructure.model.persistence.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {

    List<CartItemEntity> findByCart(Cart cart);

    @Query("""
    select
        ci
    from
        CartItemEntity ci
    join fetch
        ci.cart c
    where
        c.accountCode = :accountCode
    and
        ci.code = :cartItemCode
    """)
    Optional<CartItemEntity> findOwnCartItem(String accountCode, String cartItemCode);

    @Query("""
    select
        ci
    from
        CartItemEntity ci
    where
        ci.code in :cartItemCodes
    """)
    List<CartItemEntity> findAllByCodesIn(List<String> cartItemCodes);


}
