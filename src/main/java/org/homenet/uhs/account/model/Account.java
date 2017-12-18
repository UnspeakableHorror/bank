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

    public Double getBalance() {
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
            // The receiver gets the full amount.
            this.addFunds(transaction.getPreTaxAmount());
        }

        if(this.getId() == originId(transaction)){
            // Remove the full amount.
            this.removeFunds(transaction.getPreTaxAmount());
            // Remove the tax
            // It's not specified but the taxed amount should be sent somewhere,
            // most likely at service level with a special account or
            // something along those lines. Plus it should be another transaction
            // since it should be saved twice as different transactions,
            // one for the extracted amount
            // and another for the taxed amount.
            // Perhaps all this logic should be extracted as well, just handle
            // adding the transaction and move the addition and removal of funds
            // at service level.
            this.removeFunds(transaction.getAfterTaxAmount() - transaction.getPreTaxAmount());
        }

        this.transactions.add(transaction);
    }

    private void removeFunds(Double funds) throws InsufficientFundsException {
        Double value = Math.abs(funds);

        if(this.balance - value < 0){
            throw new InsufficientFundsException(
                    "Not enough funds in account: "
                            + this.getId()
                            + " Available: "
                            + this.getBalance()
                            + " To remove: "
                            + funds);
        }

        this.balance -= value;
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
