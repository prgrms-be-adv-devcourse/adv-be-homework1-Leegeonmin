package io.eddie.demo.domain.carts.domain.exception;

public class CouldNotFindCartItemException extends RuntimeException {

    public CouldNotFindCartItemException(String message) {
        super(message);
    }
}
