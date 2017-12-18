package org.homenet.uhs.transaction.model;

import org.homenet.uhs.account.model.Account;

/**
 * @author uh on 2017/12/16.
 */
public class DifferentBankNationalTransaction extends Transaction {

    public static final Double TAX_PERCENTAGE = 0.01;

    public DifferentBankNationalTransaction(Account originAccount, Account destinationAccount, Double amount) {
        super(originAccount, destinationAccount, amount);
    }

    @Override
    public Double calculateTax() {
        return this.getPreTaxAmount() * TAX_PERCENTAGE;
    }
}
