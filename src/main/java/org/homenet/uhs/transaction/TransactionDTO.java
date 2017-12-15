package org.homenet.uhs.transaction;

import javax.validation.constraints.NotNull;

/**
 * @author uh on 2017/12/14.
 */
public class TransactionDTO {

    @NotNull
    private final Long originAccount;

    @NotNull
    private final Long destinationAccount;

    // TODO: Replace with money
    @NotNull
    private final Double amount;

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
}
