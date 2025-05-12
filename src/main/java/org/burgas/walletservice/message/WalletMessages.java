package org.burgas.walletservice.message;

public enum WalletMessages {

    WALLET_WRONG_AMOUNT("Неверное числовое значение для определения денежного количества"),
    WALLET_NOT_FOUND("Кошелек с идентификатором %s не найден");

    private final String message;

    WalletMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
