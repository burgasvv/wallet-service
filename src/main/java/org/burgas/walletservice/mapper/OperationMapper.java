package org.burgas.walletservice.mapper;

import org.burgas.walletservice.dto.OperationRequest;
import org.burgas.walletservice.dto.OperationResponse;
import org.burgas.walletservice.entity.Operation;
import org.burgas.walletservice.handler.MapperDataHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public final class OperationMapper implements MapperDataHandler<OperationRequest, Operation, OperationResponse> {


    @Override
    public Operation toEntity(OperationRequest operationRequest) {
        return Operation.builder()
                .walletId(operationRequest.getWalletId())
                .amount(operationRequest.getAmount())
                .operationType(operationRequest.getOperationType())
                .commitedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public OperationResponse toResponse(Operation operation) {
        return OperationResponse.builder()
                .id(operation.getId())
                .walletId(operation.getWalletId())
                .amount(operation.getAmount())
                .operationType(operation.getOperationType())
                .commitedAt(operation.getCommitedAt().format(ofPattern("dd.MM.yyyy, hh:mm")))
                .build();
    }
}
