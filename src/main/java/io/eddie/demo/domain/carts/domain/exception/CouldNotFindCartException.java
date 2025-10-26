package io.eddie.demo.domain.carts.domain.exception;

public class CouldNotFindCartException extends RuntimeException {

    public CouldNotFindCartException(String message) {
        super(message);
    }
}
