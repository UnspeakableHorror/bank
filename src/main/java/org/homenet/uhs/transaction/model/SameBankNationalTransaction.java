package org.homenet.uhs.transaction.model;

import org.homenet.uhs.account.model.Account;

/**
 * @author uh on 2017/12/16.
 */
public class SameBankNationalTransaction extends Transaction {

    // keeping this here since the only allowed properties are origin, destination and amount.
    // this should be a field configured with the factory.
    public static final double TAX_PERCENTAGE = 0.0;

    public SameBankNationalTransaction(Account originAccount, Account destinationAccount, Double amount) {
        super(originAccount, destinationAccount, amount);
    }

    @Override
    public Double calculateTax() {
        return TAX_PERCENTAGE;
    }
}
