package org.homenet.uhs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author uh on 2017/12/17.
 */
@Component
public class Runners {

    @Autowired
    private RestTemplate restTemplate;

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(6);


    public void start() {
        service = Executors.newScheduledThreadPool(6);
        service.scheduleAtFixedRate(()  -> new Runner(restTemplate).run(), 1, 1, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(()  -> new Runner(restTemplate).run(), 1, 1, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(()  -> new Runner(restTemplate).run(), 1, 1, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(()  -> new Runner(restTemplate).run(), 1, 1, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        service.shutdown();
    }
}
