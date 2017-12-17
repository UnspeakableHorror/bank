package org.homenet.uhs.account.service;

import org.homenet.uhs.account.model.Account;
import org.homenet.uhs.transaction.model.Transaction;

/**
 * @author uh on 2017/12/14.
 */
public interface AccountService {

    Account getAccount(Long accountId);

    void transferFunds(Transaction transaction);
}
