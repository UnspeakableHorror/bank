package org.homenet.uhs.transaction.service.processor;

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

            Transaction transaction = TransactionFactory.getTransaction(
                    accountService.getAccount(transactionDTO.getOriginAccount()),
                    accountService.getAccount(transactionDTO.getDestinationAccount()),
                    transactionDTO.getAmount());

            accountService.transferFunds(transaction);
        });
    }
}
