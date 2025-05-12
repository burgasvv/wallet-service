package org.burgas.walletservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@Entity
public final class Operation implements Serializable {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    private UUID walletId;
    private Long amount;

    @Enumerated(value = STRING)
    private OperationType operationType;

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
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id) && Objects.equals(walletId, operation.walletId) &&
               Objects.equals(amount, operation.amount) && operationType == operation.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, walletId, amount, operationType);
    }

    @Override
    public String toString() {
        return "Operation{" +
               "id=" + id +
               ", walletId=" + walletId +
               ", amount=" + amount +
               ", operationType=" + operationType +
               '}';
    }
}
