package org.burgas.walletservice.exception;

public class WalletWrongAmountException extends RuntimeException {

    public WalletWrongAmountException(String message) {
        super(message);
    }
}
