package org.homenet.uhs.transaction.service;

import org.homenet.uhs.transaction.TransactionDTO;
/**
 * @author uh on 2017/12/14.
 */
public interface TransactionQueueingService {

    boolean post(TransactionDTO transactionDTO);
}
