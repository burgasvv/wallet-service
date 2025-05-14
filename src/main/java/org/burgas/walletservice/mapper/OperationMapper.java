package org.burgas.walletservice.mapper;

import org.burgas.walletservice.dto.OperationRequest;
import org.burgas.walletservice.dto.OperationResponse;
import org.burgas.walletservice.entity.Operation;
import org.burgas.walletservice.handler.MapperDataHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Класс для преобразования объекта запроса в объект сущности и далее в объект ответа
 */
@Component
public final class OperationMapper implements MapperDataHandler<OperationRequest, Operation, OperationResponse> {

    /**
     * Метод для преобразования объекта запроса в объект сущности
     *
     * @param operationRequest объект запроса
     * @return объект сущности
     */
    @Override
    public Operation toEntity(OperationRequest operationRequest) {
        return Operation.builder()
                .walletId(operationRequest.getWalletId())
                .amount(operationRequest.getAmount())
                .operationType(operationRequest.getOperationType())
                .commitedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Метод для преобразования объекта сущности в объект ответа
     *
     * @param operation объект сущности
     * @return объект ответа
     */
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
