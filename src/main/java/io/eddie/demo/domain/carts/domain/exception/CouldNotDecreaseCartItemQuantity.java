package io.eddie.demo.domain.carts.domain.exception;

public class CouldNotDecreaseCartItemQuantity extends RuntimeException {

    public CouldNotDecreaseCartItemQuantity(String message) {
        super(message);
    }
}
