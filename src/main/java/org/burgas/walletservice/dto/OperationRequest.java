package org.burgas.walletservice.dto;

import org.burgas.walletservice.entity.OperationType;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс для передачи данных запроса об операции
 */
public final class OperationRequest extends Request {

    private final UUID walletId;
    private final Long amount;
    private final OperationType operationType;

    public OperationRequest(UUID walletId, Long amount, OperationType operationType) {
        this.walletId = walletId;
        this.amount = amount;
        this.operationType = operationType;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public Long getAmount() {
        return amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OperationRequest that = (OperationRequest) o;
        return Objects.equals(walletId, that.walletId) && Objects.equals(amount, that.amount) && operationType == that.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, amount, operationType);
    }

    @Override
    public String toString() {
        return "OperationRequest{" +
               "walletId=" + walletId +
               ", amount=" + amount +
               ", operationType=" + operationType +
               '}';
    }
}
