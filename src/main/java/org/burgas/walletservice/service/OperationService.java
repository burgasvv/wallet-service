package org.burgas.walletservice.service;

import org.burgas.walletservice.dto.OperationRequest;
import org.burgas.walletservice.dto.OperationResponse;
import org.burgas.walletservice.entity.Operation;
import org.burgas.walletservice.exception.NotEnoughMoneyException;
import org.burgas.walletservice.exception.WalletNotFoundException;
import org.burgas.walletservice.exception.WrongOperationMoneyAmount;
import org.burgas.walletservice.log.WalletLogs;
import org.burgas.walletservice.mapper.OperationMapper;
import org.burgas.walletservice.repository.OperationRepository;
import org.burgas.walletservice.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.burgas.walletservice.log.OperationLogs.OPERATION_FOUND_BY_WALLET_ID;
import static org.burgas.walletservice.log.WalletLogs.WALLET_FOUND_BEFORE_PERFORM_OPERATION;
import static org.burgas.walletservice.message.OperationMessages.*;
import static org.burgas.walletservice.message.WalletMessages.WALLET_NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class OperationService {

    private static final Logger log = LoggerFactory.getLogger(OperationService.class);
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final WalletRepository walletRepository;

    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper, WalletRepository walletRepository) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
        this.walletRepository = walletRepository;
    }

    public List<OperationResponse> findByWalletId(final UUID walletId) {
        return this.operationRepository.findOperationsByWalletId(walletId)
                .stream()
                .peek(operation -> log.info(OPERATION_FOUND_BY_WALLET_ID.getLog(), operation))
                .map(this.operationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String performOperation(final OperationRequest operationRequest) {
        return this.walletRepository.findById(operationRequest.getWalletId())
                .stream()
                .peek(wallet -> log.info(WALLET_FOUND_BEFORE_PERFORM_OPERATION.getLog(), wallet))
                .map(
                        wallet -> {
                            if (operationRequest.getAmount() <= 0) {
                                log.info(WalletLogs.WRONG_AMOUNT_VALUE.getLog(), operationRequest.getAmount());
                                throw new WrongOperationMoneyAmount(WRONG_MONEY_AMOUNT.getMessage());
                            }

                            if ("DEPOSIT".equals(operationRequest.getOperationType().name())) {
                                wallet.setMoney(wallet.getMoney() + operationRequest.getAmount());
                                this.walletRepository.save(wallet);
                                this.operationRepository.save(this.operationMapper.toEntity(operationRequest));
                                log.info(DEPOSIT_SUCCESS.getMessage());
                                return DEPOSIT_SUCCESS.getMessage();

                            } else if (
                                    "WITHDRAW".equals(operationRequest.getOperationType().name()) &&
                                    operationRequest.getAmount() < wallet.getMoney()
                            ) {
                                wallet.setMoney(wallet.getMoney() - operationRequest.getAmount());
                                this.walletRepository.save(wallet);
                                this.operationRepository.save(this.operationMapper.toEntity(operationRequest));
                                log.info(WITHDRAW_SUCCESS.getMessage());
                                return WITHDRAW_SUCCESS.getMessage();

                            } else {
                                throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY.getMessage());
                            }
                        }
                )
                .findFirst()
                .orElseThrow(
                        () -> new WalletNotFoundException(
                                format(WALLET_NOT_FOUND.getMessage(), operationRequest.getWalletId())
                        )
                );
    }
}
