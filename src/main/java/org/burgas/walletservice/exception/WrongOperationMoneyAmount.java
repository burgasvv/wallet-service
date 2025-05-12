package org.burgas.walletservice.exception;

public class WrongOperationMoneyAmount extends RuntimeException {

    public WrongOperationMoneyAmount(String message) {
        super(message);
    }
}
