package io.eddie.demo.domain.carts.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class CartItem {

    private String code;

    private Cart cart;


    // 상품 상세 캐싱
    private String productCode;

    private String productName;

    private Long productPrice;

    private Integer quantity;



    private DeleteStatus deleteStatus = DeleteStatus.N;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public CartItem(Cart cart,String code,  String productCode, String productName, Long productPrice, Integer quantity) {
        this.cart = cart;
        this.code = code != null ? code : this.generateCode();
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }
    private String generateCode(){
        return UUID.randomUUID().toString();
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

    public void delete(){
        this.deleteStatus = DeleteStatus.D;
    }
    public Long getItemPrice() {
        return this.productPrice * this.quantity;
    }
    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Cart getCart() {return cart;}
    public DeleteStatus getDeleteStatus() {
        return deleteStatus;
    }
}
