package org.homenet.uhs.account.service;

import org.homenet.uhs.account.model.Account;
import org.homenet.uhs.transaction.model.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.homenet.uhs.account.service.TransactionState.ACCEPTED;
import static org.homenet.uhs.account.service.TransactionState.REJECTED;
import static org.homenet.uhs.constants.Banks.*;
import static org.homenet.uhs.constants.Countries.*;

/**
 * @author uh on 2017/12/16.
 */
public class InMemoryAccountService implements AccountService {
    private Logger logger = Logger.getLogger(InMemoryAccountService.class.getName());
    private Logger fileLogger;

    private Map<Long, Account> accounts = new HashMap<>();

    public InMemoryAccountService(Logger fileLogger){
        this.fileLogger = fileLogger;
        accounts.put(1L, new Account(1L, SANTANDER, ARGENTINA, 10000.0));
        accounts.put(2L, new Account(2L, SANTANDER, ARGENTINA, 1.0));
        accounts.put(3L, new Account(3L, DEUTSCHE, GERMANY, 0.0));
        accounts.put(4L, new Account(4L, LEHMAN, USA, 1000000.0));
        accounts.put(5L, new Account(5L, GALICIA, ARGENTINA, 100.0));
    }

    @Transactional
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

            try {
                origin.addTransaction(transaction);
                destination.addTransaction(transaction);

                fileLogger.info(new TransactionStatus(transaction, ACCEPTED).toString());

                logger.info("Funds transferred.");
                this.save(origin);
                this.save(destination);

            } catch (Exception e){
                //log and inform
                logger.severe(e.getMessage());

                fileLogger.info(new TransactionStatus(transaction, REJECTED, e.getMessage()).toString());
            }
        }
    }

    @Override
    public void save(Account destination) {
        //save the account to the DB
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
