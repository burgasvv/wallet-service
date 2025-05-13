package org.burgas.walletservice.log;

public enum WalletLogs {

    WRONG_AMOUNT_VALUE("Количественное значение в данном контексте неверно: {}"),
    WALLET_FOUND_BEFORE_PERFORM_OPERATION("Кошелек был найден и готов к выполнению операции: {}"),
    WALLET_CREATED("Кошелек был успешно создан: {}"),
    WALLET_FOUND_BY_ID("Кошелек был найден по идентификатору: {}");

    private final String log;

    WalletLogs(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }
}
