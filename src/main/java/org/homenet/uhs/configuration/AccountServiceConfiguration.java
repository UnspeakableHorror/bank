package org.homenet.uhs.configuration;

import org.homenet.uhs.account.service.AccountService;
import org.homenet.uhs.account.service.InMemoryAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author uh on 2017/12/16.
 */
@Profile(Profiles.LOCAL)
@Configuration
public class AccountServiceConfiguration {

    @Bean
    public Logger fileLogger() throws IOException {
        Logger logger = Logger.getLogger("FileLogger");

        FileHandler fileHandler = new FileHandler("transactions.txt");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

        return logger;
    }

    @Bean
    public AccountService accountService(Logger fileLogger){
        return new InMemoryAccountService(fileLogger);
    }
}
