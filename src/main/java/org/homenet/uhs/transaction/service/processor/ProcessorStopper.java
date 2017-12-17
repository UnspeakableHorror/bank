package org.homenet.uhs.transaction.service.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

/**
 * @author uh on 2017/12/16.
 */
@Component
public class ProcessorStopper implements ApplicationListener<ContextClosedEvent> {

    private Logger logger = Logger.getLogger(ProcessorStopper.class.getName());

    @Autowired
    private TransactionProcessor transactionProcessor;

    @Autowired
    private ExecutorService executorService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Stopping...");
        //transactionProcessor.stop();
        System.out.println("aa");
        //        try {
//            executorService.awaitTermination(1, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
