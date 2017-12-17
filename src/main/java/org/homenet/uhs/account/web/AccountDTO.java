package org.homenet.uhs.account.web;

import org.homenet.uhs.account.model.Account;
/**
 * @author uh on 2017/12/16.
 */
public class AccountDTO {
    private final long id;
    private final String bank;
    private final String country;
    private final double balance;

    public AccountDTO(long id, String bank, String country, double balance) {
        this.id = id;
        this.bank = bank;
        this.country = country;
        this.balance = balance;
    }

    public AccountDTO(Account account) {
        this(account.getId(), account.getBank(), account.getCountry(), account.getBalance());
    }

    public long getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public String getCountry() {
        return country;
    }

    public double getBalance() {
        return balance;
    }
}
