package org.homenet.uhs.transaction.model;

import org.homenet.uhs.account.model.Account;

/**
 * @author uh on 2017/12/16.
 */
public class SameBankNationalTransaction extends Transaction {

    public SameBankNationalTransaction(Account originAccount, Account destinationAccount, Double amount) {
        super(originAccount, destinationAccount, amount);
    }

    @Override
    public Double calculateTax() {
        return 0.0;
    }
}
