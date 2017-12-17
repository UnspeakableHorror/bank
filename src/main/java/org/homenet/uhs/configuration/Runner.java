package org.homenet.uhs.configuration;

import org.homenet.uhs.transaction.web.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author uh on 2017/12/16.
 */
@Component
public class Runner {
    private Logger logger = Logger.getLogger(Runner.class.getName());

    private RestTemplate restTemplate;
    private boolean active;

    @Autowired
    public Runner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.active = true;
    }

    public void run(){
        logger.info("Starting test...");

        while(active) {
            TransactionDTO transactionDTO = new TransactionDTO(1L, 5L,
                    new Random().nextDouble()*10);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/api/transaction",
                    transactionDTO, String.class);
            logger.info(responseEntity.toString());

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        active = false;
    }
}
