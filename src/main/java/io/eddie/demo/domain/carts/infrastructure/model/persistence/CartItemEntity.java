package io.eddie.demo.domain.carts.infrastructure.model.persistence;

import io.eddie.demo.common.model.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "CartItem")
public class CartItemEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    // 상품 상세 캐싱
    @Setter
    private String productCode;

    @Setter
    private String productName;

    @Setter
    private Long productPrice;

    @Setter
    private Integer quantity;

    @Builder
    public CartItemEntity(CartEntity cart,String productCode, String productName, Long productPrice, Integer quantity) {
        this.cart = cart;
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public boolean canDecrease() {
        return this.quantity > 1;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }

    public Long getItemPrice() {
        return this.productPrice * this.quantity;
    }

}
