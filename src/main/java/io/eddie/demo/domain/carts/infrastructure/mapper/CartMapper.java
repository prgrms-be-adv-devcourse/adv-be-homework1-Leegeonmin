package io.eddie.demo.domain.carts.infrastructure.mapper;

import io.eddie.demo.common.model.persistence.BaseEntity;
import io.eddie.demo.domain.carts.domain.model.Cart;
import io.eddie.demo.domain.carts.domain.model.CartItem;
import io.eddie.demo.domain.carts.infrastructure.model.dto.CartDescription;
import io.eddie.demo.domain.carts.infrastructure.model.dto.CartItemDescription;
import io.eddie.demo.domain.carts.infrastructure.model.persistence.CartEntity;
import io.eddie.demo.domain.carts.infrastructure.model.persistence.CartItemEntity;

import java.util.Objects;

public class CartMapper {
    public static CartDescription toCartDescription(Cart cart) {
        return new CartDescription(
                cart.getCode(),
                cart.getCartItems()
                        .stream()
                        .map(CartMapper::toCartItemDescription)
                        .toList(),
                cart.getTotalPrice()
        );
    }

    public static CartItemDescription toCartItemDescription(CartItem cartItem) {
        return new CartItemDescription(
                cartItem.getCode(),
                cartItem.getProductCode(),
                cartItem.getProductName(),
                cartItem.getProductPrice(),
                cartItem.getQuantity(),
                cartItem.getCreatedAt()
        );
    }

    public static CartEntity toEntity(Cart cart) {
        return CartEntity.builder()
                .accountCode(cart.getAacountCode())
                .build();
    }


    public static Cart toDomain(CartEntity entity){
        return new Cart(
                entity.getCode(),
                entity.getAccountCode(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static CartItem toDomain(CartItemEntity entity){
        return new CartItem(
                CartMapper.toDomain(entity.getCart()),
                entity.getCode(),
                entity.getProductCode(),
                entity.getProductName(),
                entity.getProductPrice(),
                entity.getQuantity()
        );
    }

    public static void applyToEntity(CartItem domain, CartItemEntity entity) {

        entity.setProductCode(domain.getProductCode());
        entity.setProductName(domain.getProductName());
        entity.setProductPrice(domain.getProductPrice());
        entity.setQuantity(domain.getQuantity());
        entity.setDeleteStatus(Objects.equals(domain.getDeleteStatus().toString(), "N") ?
                BaseEntity.DeleteStatus.N :
                BaseEntity.DeleteStatus.D);
    }

}
