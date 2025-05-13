package org.burgas.walletservice.exception;

public class WrongOperationTypeException extends RuntimeException {

    public WrongOperationTypeException(String message) {
        super(message);
    }
}
