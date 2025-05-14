package org.burgas.walletservice.dto;

import org.burgas.walletservice.entity.OperationType;

import java.util.UUID;

/**
 * Класс для получения ответа об операции
 */
@SuppressWarnings("ALL")
public final class OperationResponse extends Response {

    private UUID id;
    private UUID walletId;
    private Long amount;
    private OperationType operationType;
    private String commitedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getCommitedAt() {
        return commitedAt;
    }

    public void setCommitedAt(String commitedAt) {
        this.commitedAt = commitedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final OperationResponse operationResponse;

        public Builder() {
            operationResponse = new OperationResponse();
        }

        public Builder id(UUID id) {
            this.operationResponse.id = id;
            return this;
        }

        public Builder walletId(UUID walletId) {
            this.operationResponse.walletId = walletId;
            return this;
        }

        public Builder amount(Long amount) {
            this.operationResponse.amount = amount;
            return this;
        }

        public Builder operationType(OperationType operationType) {
            this.operationResponse.operationType = operationType;
            return this;
        }

        public Builder commitedAt(String commitedAt) {
            this.operationResponse.commitedAt = commitedAt;
            return this;
        }

        public OperationResponse build() {
            return this.operationResponse;
        }
    }
}
