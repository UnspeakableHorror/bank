package org.homenet.uhs.transaction.web;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author uh on 2017/12/14.
 */
public class TransactionDTO {

    @NotNull
    private Long originAccount;

    @NotNull
    private Long destinationAccount;

    // TODO: Replace with money
    @NotNull
    private Double amount;

    public TransactionDTO() {
    }

    public TransactionDTO(Long originAccount, Long destinationAccount, Double amount) {
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public Long getOriginAccount() {
        return originAccount;
    }

    public Long getDestinationAccount() {
        return destinationAccount;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "originAccount=" + originAccount +
                ", destinationAccount=" + destinationAccount +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return Objects.equals(getOriginAccount(), that.getOriginAccount()) &&
                Objects.equals(getDestinationAccount(), that.getDestinationAccount()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOriginAccount(), getDestinationAccount(), getAmount());
    }
}
