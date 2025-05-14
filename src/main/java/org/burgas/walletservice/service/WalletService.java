package org.burgas.walletservice.service;

import org.burgas.walletservice.entity.Wallet;
import org.burgas.walletservice.exception.WalletNotFoundException;
import org.burgas.walletservice.exception.WalletWrongAmountException;
import org.burgas.walletservice.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.lang.String.format;
import static org.burgas.walletservice.log.WalletLogs.WALLET_CREATED;
import static org.burgas.walletservice.log.WalletLogs.WALLET_FOUND_BY_ID;
import static org.burgas.walletservice.message.WalletMessages.WALLET_NOT_FOUND;
import static org.burgas.walletservice.message.WalletMessages.WALLET_WRONG_AMOUNT;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

/**
 * Класс для работы с методами кошелька
 */
@Service
@Transactional(readOnly = true, propagation = SUPPORTS)
public class WalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Метод получения кошелька по его идентификатору
     *
     * @param walletId идентификатор кошелька
     * @return Полученный объект кошелька
     */
    public Wallet findById(final UUID walletId) {
        return this.walletRepository.findById(walletId)
                .stream()
                .peek(wallet -> log.info(WALLET_FOUND_BY_ID.getLog(), wallet))
                .findFirst()
                .orElseThrow(
                        () -> new WalletNotFoundException(format(WALLET_NOT_FOUND.getMessage(), walletId))
                );
    }


    /**
     * Метод создания кошелька и сохранения его в базе
     *
     * @param money стартовое денежное значение (необязательное)
     * @return Полученный объект кошелька
     */
    @Transactional(
            isolation = READ_COMMITTED, propagation = REQUIRED,
            rollbackFor = Exception.class
    )
    public Wallet create(Long money) {
        if (money == null) {
            money = 0L;
        }
        if (money >= 0) {
            Wallet saved = this.walletRepository.save(Wallet.builder().money(money).build());
            log.info(WALLET_CREATED.getLog(), saved);
            return saved;

        } else {
            throw new WalletWrongAmountException(WALLET_WRONG_AMOUNT.getMessage());
        }
    }
}
