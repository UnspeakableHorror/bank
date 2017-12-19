package org.homenet.uhs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author uh on 2017/12/17.
 */
@Component
public class Runners {

    private List<Runner> runners = new ArrayList<>();

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(6);

    @Autowired
    public Runners(RestTemplate restTemplate){
        runners.add(new Runner(restTemplate));
        runners.add(new Runner(restTemplate));
        runners.add(new Runner(restTemplate));
        runners.add(new Runner(restTemplate));
    }


    public void start() {
        service = Executors.newScheduledThreadPool(6);

        runners.forEach( it ->
                service.scheduleAtFixedRate(it::run, 1, 3, TimeUnit.MILLISECONDS)
        );
    }

    public void stop() {
        runners.forEach(Runner::stop);
        service.shutdown();
    }
}
