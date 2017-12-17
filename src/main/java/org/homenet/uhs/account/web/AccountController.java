package org.homenet.uhs.account.web;

import org.homenet.uhs.account.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uh on 2017/12/14.
 */
@RestController
@RequestMapping("api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("account/{accountId}")
    public AccountDTO getAccount(@PathVariable Long accountId){
        return new AccountDTO(accountService.getAccount(accountId));
    }
}
