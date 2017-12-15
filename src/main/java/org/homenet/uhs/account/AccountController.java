package org.homenet.uhs.account;

import org.homenet.uhs.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("account")
    public String getAccount(){
        return "";
    }
}
