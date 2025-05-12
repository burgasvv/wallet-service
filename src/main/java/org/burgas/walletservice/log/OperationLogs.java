package org.burgas.walletservice.log;

public enum OperationLogs {

    OPERATION_FOUND_BY_WALLET_ID("Операция была найдена по идентификатору кошелька: {}");

    private final String log;

    OperationLogs(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }
}
