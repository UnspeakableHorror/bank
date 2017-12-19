package org.homenet.uhs.configuration;

import org.homenet.uhs.transaction.web.TransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author uh on 2017/12/16.
 */
public class Runner {
    private Logger logger = Logger.getLogger(Runner.class.getName());

    private RestTemplate restTemplate;
    private boolean active;

    public Runner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void run(){
        logger.info("Starting test...");
        this.active = true;

        while(active) {
            long account1 = new Random().nextInt(6) + 1;
            long account2 = new Random().nextInt(6) + 1;

            TransactionDTO transactionDTO = new TransactionDTO(account1, account2,
                    new Random().nextDouble()*1000);
            ResponseEntity<String> responseEntity = ResponseEntity.ok().build();
            try {

                responseEntity = restTemplate.postForEntity("http://localhost:8080/api/transaction",
                        transactionDTO, String.class);
                logger.info(responseEntity.toString());
            } catch (RestClientException e){
                HttpClientErrorException errorException = ((HttpClientErrorException) e);
                logger.severe("Error "  + errorException.getRawStatusCode()
                        + " - " + errorException.getResponseBodyAsString());
            }

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
