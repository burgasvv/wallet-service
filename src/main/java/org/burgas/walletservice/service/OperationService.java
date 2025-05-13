package org.burgas.walletservice.service;

import org.burgas.walletservice.entity.Operation;
import org.burgas.walletservice.exception.NotEnoughMoneyException;
import org.burgas.walletservice.exception.WalletNotFoundException;
import org.burgas.walletservice.exception.WrongOperationMoneyAmount;
import org.burgas.walletservice.log.WalletLogs;
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
    private final WalletRepository walletRepository;

    public OperationService(OperationRepository operationRepository, WalletRepository walletRepository) {
        this.operationRepository = operationRepository;
        this.walletRepository = walletRepository;
    }

    public List<Operation> findByWalletId(final UUID walletId) {
        return this.operationRepository.findOperationsByWalletId(walletId)
                .stream()
                .peek(operation -> log.info(OPERATION_FOUND_BY_WALLET_ID.getLog(), operation))
                .collect(Collectors.toList());
    }

    @Transactional(
            isolation = SERIALIZABLE, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public String performOperation(final Operation operation) {
        return this.walletRepository.findById(operation.getWalletId())
                .stream()
                .peek(wallet -> log.info(WALLET_FOUND_BEFORE_PERFORM_OPERATION.getLog(), wallet))
                .map(
                        wallet -> {
                            if (operation.getAmount() <= 0) {
                                log.info(WalletLogs.WRONG_AMOUNT_VALUE.getLog(), operation.getAmount());
                                throw new WrongOperationMoneyAmount(WRONG_MONEY_AMOUNT.getMessage());
                            }

                            if ("DEPOSIT".equals(operation.getOperationType().name())) {
                                wallet.setMoney(wallet.getMoney() + operation.getAmount());
                                this.walletRepository.save(wallet);
                                this.operationRepository.save(operation);
                                log.info(DEPOSIT_SUCCESS.getMessage());
                                return DEPOSIT_SUCCESS.getMessage();

                            } else if (
                                    "WITHDRAW".equals(operation.getOperationType().name()) &&
                                    operation.getAmount() < wallet.getMoney()
                            ) {
                                wallet.setMoney(wallet.getMoney() - operation.getAmount());
                                this.walletRepository.save(wallet);
                                this.operationRepository.save(operation);
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
                                format(WALLET_NOT_FOUND.getMessage(), operation.getWalletId())
                        )
                );
    }
}
