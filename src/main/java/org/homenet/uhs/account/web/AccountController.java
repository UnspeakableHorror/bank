package org.homenet.uhs.account.web;

import org.homenet.uhs.account.model.Account;
import org.homenet.uhs.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    public ResponseEntity<AccountDTO>  getAccount(@PathVariable Long accountId){
        Optional<Account> account = accountService.getAccount(accountId);

        if(account.isPresent()){
            return ResponseEntity.ok(new AccountDTO(account.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
