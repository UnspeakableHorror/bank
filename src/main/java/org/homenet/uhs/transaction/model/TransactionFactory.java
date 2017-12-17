package org.homenet.uhs.transaction.model;

import org.homenet.uhs.account.model.Account;

import java.util.function.BiFunction;

/**
 * @author uh on 2017/12/16.
 */
public class TransactionFactory {

    /**
     * Return a National or International Transaction.
     * @param origin
     * @param destination
     * @param amount
     * @return
     */
    public static Transaction getTransaction(Account origin, Account destination, Double amount) {
        BiFunction<Account, Account, Boolean> sameCountry = (a1, a2) -> a1.getCountry().equalsIgnoreCase(a2.getCountry());

        return sameCountry.apply(origin, destination) ?
                nationalTransaction(origin, destination, amount) :
                new InternationalTransaction(origin, destination, amount);
    }

    private static Transaction nationalTransaction(Account origin, Account destination, Double amount){
        BiFunction<Account, Account, Boolean> sameBank = (a1, a2) -> a1.getBank().equalsIgnoreCase(a2.getBank());

        return sameBank.apply(origin, destination) ?
                new SameBankNationalTransaction(origin, destination, amount) :
                new DifferentBankNationalTransaction(origin, destination, amount);
    }
}
