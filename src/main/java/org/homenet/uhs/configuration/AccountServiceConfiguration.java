package org.homenet.uhs.configuration;

import org.homenet.uhs.account.service.AccountService;
import org.homenet.uhs.account.service.InMemoryAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author uh on 2017/12/16.
 */
@Profile(Profiles.LOCAL)
@Configuration
public class AccountServiceConfiguration {

    @Bean
    public AccountService accountService(){
        return new InMemoryAccountService();
    }
}
