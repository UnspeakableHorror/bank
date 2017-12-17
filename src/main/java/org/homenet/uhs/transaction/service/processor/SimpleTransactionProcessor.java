package org.homenet.uhs.transaction.service.processor;

import org.homenet.uhs.account.service.AccountService;
import org.homenet.uhs.transaction.service.queue.TransactionQueueingService;
import org.springframework.context.Lifecycle;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author uh on 2017/12/16.
 */
public class SimpleTransactionProcessor implements TransactionProcessor, Lifecycle {
    private Logger logger = Logger.getLogger(SimpleTransactionProcessor.class.getName());

    private boolean isActive = true;
    private AccountService accountService;
    private ExecutorService executorService;
    private TransactionQueueingService transactionQueueingService;


    public SimpleTransactionProcessor(AccountService accountService, ExecutorService executorService,
                                      TransactionQueueingService transactionQueueingService) {
        this.accountService = accountService;
        this.executorService = executorService;
        this.transactionQueueingService = transactionQueueingService;
    }

    @Override
    public void start() {
        logger.info("Starting...");
        while(!executorService.isShutdown() || isActive) {
            if(transactionQueueingService.hasElements()) {
                logger.info("Submitting task...");
                executorService.submit(new Processor(accountService, transactionQueueingService));
            } else {
                try {
                    logger.info("No tasks found. Sleeping.");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Failed!", e);
                }
            }
        }
    }

    @PreDestroy
    @Override
    public void stop() {
        isActive = false;
        logger.info("Shutting down...");
//        try {
            executorService.shutdown();
            //executorService.awaitTermination(3, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//           logger.log(Level.SEVERE, "Failed!", e);
//        }
    }

    @Override
    public boolean isRunning() {
        return executorService.isShutdown();
    }

//    @Override
//    public void onApplicationEvent(ContextClosedEvent event) {
//        logger.info("Stopping...");
//        stop();
//    }

}
