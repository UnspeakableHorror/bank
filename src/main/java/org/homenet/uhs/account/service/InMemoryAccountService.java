package org.homenet.uhs.account.service;

import org.homenet.uhs.account.model.Account;
import org.homenet.uhs.transaction.model.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import static org.homenet.uhs.constants.Banks.*;
import static org.homenet.uhs.constants.Countries.*;

/**
 * @author uh on 2017/12/16.
 */
public class InMemoryAccountService implements AccountService {
    private Logger logger = Logger.getLogger(InMemoryAccountService.class.getName());

    private Map<Long, Account> accounts = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public InMemoryAccountService(){
        accounts.put(1L, new Account(1L, SANTANDER, ARGENTINA, 10000.0));
        accounts.put(2L, new Account(2L, SANTANDER, ARGENTINA, 1.0));
        accounts.put(3L, new Account(3L, DEUTSCHE, GERMANY, 0.0));
        accounts.put(4L, new Account(4L, LEHMAN, USA, 1000000.0));
        accounts.put(5L, new Account(5L, GALICIA, ARGENTINA, 100.0));
    }

    public synchronized void transferFunds(Transaction transaction){
        logger.info("About to transfer funds.");

        if(canBeProcessed(transaction)){

            logger.info("Transferring funds from account: "
                    + transaction.getOriginAccount().getId()
                    + " to account: "
                    + transaction.getDestinationAccount().getId()
                    + ", amount: "
                    + transaction.getPreTaxAmount()
                    + ", taxed: "
                    + (transaction.getPreTaxAmount() - transaction.getTaxedAmount())
            );

            Account origin = accounts.get(transaction.getOriginAccount().getId());
            Account destination = accounts.get(transaction.getDestinationAccount().getId());

            origin.removeFunds(transaction.getTaxedAmount());
            destination.addFunds(transaction.getTaxedAmount());

            logger.info("Funds transferred.");
        }
    }

    public Account getAccount(Long accountId){
        return accounts.get(accountId);
    }

    private boolean canBeProcessed(Transaction transaction) {
        return accounts.containsKey(transaction.getOriginAccount().getId())
                && accounts.containsKey(transaction.getDestinationAccount().getId())
                && transaction.isValid();
    }

}
