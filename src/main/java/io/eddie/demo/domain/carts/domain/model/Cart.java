package io.eddie.demo.domain.carts.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {

    private String code;

    private String aacountCode;
    private List<CartItem> cartItems = new ArrayList<>();

    private DeleteStatus deleteStatus = DeleteStatus.N;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Cart(String aacountCode) {
        this.aacountCode = aacountCode;
    }
    public Cart(String code,
                String aacountCode,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.code = code;
        this.aacountCode = aacountCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    private String generateCode(){
        return UUID.randomUUID().toString();
    }
    public Long getTotalPrice() {
        return cartItems.stream()
                .map(CartItem::getItemPrice)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public String getAacountCode() {
        return aacountCode;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public String getCode() {
        return code;
    }

}
