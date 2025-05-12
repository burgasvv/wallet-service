package org.burgas.walletservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.GenerationType.UUID;

@Entity
public final class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    private Long money;

    public UUID getId() {
        return id;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long amount) {
        this.money = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(money, wallet.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, money);
    }

    @Override
    public String toString() {
        return "Wallet{" +
               "id=" + id +
               ", money=" + money +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Wallet wallet;

        public Builder() {
            wallet = new Wallet();
        }

        public Builder money(Long money) {
            this.wallet.money = money;
            return this;
        }

        public Wallet build() {
            return this.wallet;
        }
    }
}
