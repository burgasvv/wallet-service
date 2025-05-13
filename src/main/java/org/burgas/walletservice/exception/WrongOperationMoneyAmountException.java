package org.burgas.walletservice.exception;

public class WrongOperationMoneyAmountException extends RuntimeException {

    public WrongOperationMoneyAmountException(String message) {
        super(message);
    }
}
