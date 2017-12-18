package org.homenet.uhs.account.model;

import org.homenet.uhs.account.service.InsufficientFundsException;
import org.homenet.uhs.transaction.model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author uh on 2017/12/16.
 */
public class Account {
    private long id;
    private String bank;
    private String country;
    private double balance;
    private List<Transaction> transactions;

    public Account(long id, String bank, String country, double balance) {
        this.id = id;
        this.bank = bank;
        this.country = country;
        this.balance = balance;
        this.transactions = new ArrayList<>();
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

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public synchronized void addTransaction(Transaction transaction) throws InsufficientFundsException{

        if(originId(transaction) == destinationId(transaction)){
            throw new IllegalArgumentException("Cannot transfer funds to itself.");
        }

        if(this.getId() == destinationId(transaction)){
            this.addFunds(transaction.getAfterTaxAmount());
        }

        if(this.getId() == originId(transaction)){
            this.removeFunds(transaction.getAfterTaxAmount());
        }

        this.transactions.add(transaction);
    }

    private void removeFunds(Double funds) throws InsufficientFundsException {
        if(this.balance - funds < 0){
            throw new InsufficientFundsException(
                    "Not enough funds in account: "
                            + this.getId()
                            + " Available: "
                            + this.getBalance()
                            + " To remove: "
                            + funds);
        }

        this.balance -= funds;
    }

    private void addFunds(Double funds) {
        this.balance += funds;
    }

    private long originId(Transaction transaction){
        return transaction.getOriginAccount().getId();
    }

    private long destinationId(Transaction transaction) {
        return transaction.getDestinationAccount().getId();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id= " + id +
                ", bank= '" + bank + '\'' +
                ", country= '" + country + '\'' +
                ", balance= " + balance +
                ", transactions= " + transactions.size() +
                '}';
    }
}
