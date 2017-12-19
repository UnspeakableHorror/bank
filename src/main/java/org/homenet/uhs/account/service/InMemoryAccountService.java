package org.homenet.uhs.account.service;

import org.homenet.uhs.account.model.Account;
import org.homenet.uhs.transaction.model.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static org.homenet.uhs.account.service.TransactionState.ACCEPTED;
import static org.homenet.uhs.account.service.TransactionState.REJECTED;
import static org.homenet.uhs.constants.Banks.*;
import static org.homenet.uhs.constants.Countries.*;

/**
 * Account service that runs in memory, useful for testing.
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

            try {

                Account originAccount = transaction.getOriginAccount();

                Account destinationAccount = transaction.getDestinationAccount();

                logger.info("Transferring funds from account: "
                        + originAccount
                        + " to account: "
                        + destinationAccount
                        + ", amount: "
                        + transaction.getPreTaxAmount()
                        + ", taxed: "
                        + (transaction.getPreTaxAmount() - transaction.getAfterTaxAmount())
                );

                originAccount.addTransaction(transaction);
                destinationAccount.addTransaction(transaction);

                fileLogger.info(new TransactionStatus(transaction, ACCEPTED).toString());

                logger.info("Funds transferred.");
                this.save(originAccount);
                this.save(destinationAccount);

            } catch (Exception e){
                //log and inform
                logger.severe(e.getMessage());
                fileLogger.info(new TransactionStatus(transaction, REJECTED, e.getMessage()).toString());
            }
        }
    }

    @Override
    public void save(Account account) {
        // for an in memory operation this is not really needed
        // since it's the same object but I added it for the sake
        // of completeness.
        this.accounts.put(account.getId(), account);
    }

    public Optional<Account> getAccount(Long accountId){
        return Optional.ofNullable(accounts.get(accountId));
    }

    private boolean canBeProcessed(Transaction transaction) {
        return transaction.isValid();
    }

}
