package org.homenet.uhs.transaction.model;

import org.homenet.uhs.account.model.Account;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author uh on 2017/12/16.
 */
public abstract class Transaction {

    @NotNull
    private final Account originAccount;

    @NotNull
    private final Account destinationAccount;

    @NotNull
    private final Double amount;

    public Transaction(Account originAccount, Account destinationAccount, Double amount) {
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public Account getOriginAccount() {
        return originAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public Double getPreTaxAmount() {
        return amount;
    }

    /**
     * @return the value of the transaction after tax.
     */
    public Double getTaxedAmount() {
        return amount - calculateTax();
    }

    public abstract Double calculateTax();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(originAccount, that.originAccount) &&
                Objects.equals(destinationAccount, that.destinationAccount) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originAccount, destinationAccount, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "originAccount=" + originAccount +
                ", destinationAccount=" + destinationAccount +
                ", amount=" + amount +
                '}';
    }

    public boolean isValid() {
        return this.getOriginAccount() != null
                && this.getDestinationAccount() != null
                && this.getPreTaxAmount() != null
                && !this.getDestinationAccount().equals(this.getOriginAccount());
    }
}
