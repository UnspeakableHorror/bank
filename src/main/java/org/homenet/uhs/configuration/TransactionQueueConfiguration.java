package org.homenet.uhs.configuration;

import org.homenet.uhs.transaction.service.queue.InMemoryTransactionQueueingService;
import org.homenet.uhs.transaction.service.queue.TransactionQueueingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

/**
 * @author uh on 2017/12/14.
 */
@Profile(Profiles.LOCAL)
@Configuration
@Order(2)
public class TransactionQueueConfiguration {

    @Bean
    public TransactionQueueingService transactionQueueingService(){
        return new InMemoryTransactionQueueingService();
    }
}
