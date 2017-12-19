package org.homenet.uhs.account.service;

import org.homenet.uhs.account.model.Account;
import org.homenet.uhs.transaction.model.Transaction;

import java.util.Optional;

/**
 * @author uh on 2017/12/14.
 */
public interface AccountService {

    Optional<Account> getAccount(Long accountId);

    void transferFunds(Transaction transaction);

    void save(Account destination);
}
