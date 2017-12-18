package org.homenet.uhs.configuration;

import org.homenet.uhs.account.service.AccountService;
import org.homenet.uhs.transaction.service.processor.SimpleTransactionProcessor;
import org.homenet.uhs.transaction.service.processor.TransactionProcessor;
import org.homenet.uhs.transaction.service.queue.TransactionQueueingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author uh on 2017/12/14.
 */
@Order(10)
@Configuration
public class TransactionQueueProcessorConfiguration {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionQueueingService transactionQueueingService;

    @Bean
    public ExecutorService executorService(){
       return Executors.newCachedThreadPool();
    }

    @Bean
    public TransactionProcessor transactionProcessorHolder(ExecutorService executorService){
        return new SimpleTransactionProcessor(accountService, executorService, transactionQueueingService);
    }
}
