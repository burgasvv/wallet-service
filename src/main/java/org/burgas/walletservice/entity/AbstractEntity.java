package org.burgas.walletservice.entity;

/**
 * Запечатанный класс для контроля сущностей
 */
public abstract sealed class AbstractEntity permits Wallet, Operation {
}
