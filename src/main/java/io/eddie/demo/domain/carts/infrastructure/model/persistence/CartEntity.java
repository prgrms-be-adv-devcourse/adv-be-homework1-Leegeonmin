package io.eddie.demo.domain.carts.infrastructure.model.persistence;

import io.eddie.demo.common.model.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "Cart")
public class CartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto identity sequence, uui
    private Long id;

    private String accountCode;

    @OneToMany(mappedBy = "cart")
    List<CartItemEntity> cartItems = new ArrayList<>();

    @Builder
    public CartEntity(String accountCode) {
        this.accountCode = accountCode;
    }

    public Long getTotalPrice() {
        return cartItems.stream()
                .map(CartItemEntity::getItemPrice)
                .reduce(Long::sum)
                .orElse(0L);
    }

}
