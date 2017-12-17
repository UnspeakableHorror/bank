package org.homenet.uhs;

import org.homenet.uhs.transaction.service.processor.TransactionProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author uh on 2017/12/12.
 */
@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        TransactionProcessor transactionProcessor = context.getBean(TransactionProcessor.class);
        transactionProcessor.start();

    }

}
