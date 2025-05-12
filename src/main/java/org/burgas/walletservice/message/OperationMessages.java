package org.burgas.walletservice.message;

public enum OperationMessages {

    DEPOSIT_SUCCESS("Операция DEPOSIT выполнена успешно"),
    WITHDRAW_SUCCESS("Операция WITHDRAW выполнена успешно"),
    NOT_ENOUGH_MONEY("Недостаточно средств для завершения операции"),
    WRONG_MONEY_AMOUNT("Введенное денежное количество неверно");

    private final String message;

    OperationMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
