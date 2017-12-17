package org.homenet.uhs.transaction.model;

import org.homenet.uhs.account.model.Account;

/**
 * @author uh on 2017/12/16.
 */
public class InternationalTransaction extends Transaction {

    public static final double TAX_PERCENTAGE = 0.05;

    public InternationalTransaction(Account originAccount, Account destinationAccount, Double amount) {
        super(originAccount, destinationAccount, amount);
    }

    /**
     * Calculates the amount to tax, 5% for international transfers
     * @return the taxed amount.
     */
    @Override
    public Double calculateTax() {
        return this.getPreTaxAmount() * TAX_PERCENTAGE;
    }
}
