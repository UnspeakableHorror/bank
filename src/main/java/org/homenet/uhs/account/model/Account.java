package org.homenet.uhs.account.model;

/**
 * @author uh on 2017/12/16.
 */
public class Account {
    private long id;
    private String bank;
    private String country;
    private double balance;

    public Account(long id, String bank, String country, double balance) {
        this.id = id;
        this.bank = bank;
        this.country = country;
        this.balance = balance;
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

    public void removeFunds(Double funds) {
        this.balance -= funds;
    }

    public void addFunds(Double funds) {
        this.balance += funds;
    }
}
