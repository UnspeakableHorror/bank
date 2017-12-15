package org.homenet.uhs.transaction.service;

import org.homenet.uhs.transaction.TransactionDTO;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author uh on 2017/12/14.
 */
public class InMemoryTransactionQueueingService implements TransactionQueueingService {

    private static final int MAX_ELEMENTS  = 8192;
    private final Deque<TransactionDTO> queue = new ArrayDeque<>(MAX_ELEMENTS);

    @Override
    public synchronized boolean post(TransactionDTO transactionDTO) {
        return queue.offer(transactionDTO);
    }
}
