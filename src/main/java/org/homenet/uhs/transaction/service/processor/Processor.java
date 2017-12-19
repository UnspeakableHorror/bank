package org.homenet.uhs.transaction.service.processor;

import org.homenet.uhs.account.model.Account;
import org.homenet.uhs.account.service.AccountService;
import org.homenet.uhs.transaction.model.Transaction;
import org.homenet.uhs.transaction.model.TransactionFactory;
import org.homenet.uhs.transaction.service.queue.QueueElement;
import org.homenet.uhs.transaction.service.queue.TransactionQueueingService;
import org.homenet.uhs.transaction.web.TransactionDTO;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author uh on 2017/12/16.
 */
class Processor implements Runnable{
    private Logger logger = Logger.getLogger(Processor.class.getName());

    private final AccountService accountService;
    private final TransactionQueueingService transactionQueueingService;

    Processor(AccountService accountService, TransactionQueueingService transactionQueueingService) {
        this.accountService = accountService;
        this.transactionQueueingService = transactionQueueingService;
    }

    @Override
    public void run() {
        logger.info("Processing...");

        Optional<QueueElement<TransactionDTO>> next = transactionQueueingService.next();
        next.ifPresent(it -> {
            logger.info("Transaction found. Processing...");
            TransactionDTO transactionDTO = it.getElement();
            logger.info(transactionDTO.toString());

            Optional<Account> origin = accountService.getAccount(transactionDTO.getOriginAccount());
            Optional<Account> destination = accountService.getAccount(transactionDTO.getDestinationAccount());

            if(!origin.isPresent()){
                // we can't inform the user directly,
                // just log it for now.
                logger.severe("Account with id: " + transactionDTO.getOriginAccount() + " not found.");
                return;
            }

            if(!destination.isPresent()){
                // we can't inform the user directly,
                // just log it for now.
                logger.severe("Account with id: " + transactionDTO.getDestinationAccount() + " not found.");
                return;
            }

            Transaction transaction = TransactionFactory.getTransaction(
                    origin.get(),
                    destination.get(),
                    transactionDTO.getAmount());

            accountService.transferFunds(transaction);
        });
    }
}
