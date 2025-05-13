package org.burgas.walletservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@Entity
public final class Operation extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    private UUID walletId;
    private Long amount;

    @Enumerated(value = STRING)
    private OperationType operationType;
    private LocalDateTime commitedAt;

    public UUID getId() {
        return id;
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

    public LocalDateTime getCommitedAt() {
        return commitedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id) && Objects.equals(walletId, operation.walletId) &&
               Objects.equals(amount, operation.amount) && operationType == operation.operationType && Objects.equals(commitedAt, operation.commitedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, walletId, amount, operationType, commitedAt);
    }

    @Override
    public String toString() {
        return "Operation{" +
               "id=" + id +
               ", walletId=" + walletId +
               ", amount=" + amount +
               ", operationType=" + operationType +
               ", commitedAt=" + commitedAt +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Operation operation;

        public Builder() {
            operation = new Operation();
        }

        public Builder walletId(UUID walletId) {
            this.operation.walletId = walletId;
            return this;
        }

        public Builder amount(Long amount) {
            this.operation.amount = amount;
            return this;
        }

        public Builder operationType(OperationType operationType) {
            this.operation.operationType = operationType;
            return this;
        }

        public Builder commitedAt(LocalDateTime commitedAt) {
            this.operation.commitedAt = commitedAt;
            return this;
        }

        public Operation build() {
            return this.operation;
        }
    }
}
