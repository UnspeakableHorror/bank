package org.homenet.uhs.account.service;


/**
 * @author uh on 2017/12/17.
 */
public class InsufficientFundsException extends RuntimeException{

    public InsufficientFundsException(String error) {
        super(error);
    }
}
