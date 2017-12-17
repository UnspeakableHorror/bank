package org.homenet.uhs.transaction.service.queue;

import org.homenet.uhs.transaction.web.TransactionDTO;

import java.util.Optional;

/**
 * @author uh on 2017/12/14.
 */
public interface TransactionQueueingService {

    boolean post(TransactionDTO transactionDTO);

    Optional<QueueElement<TransactionDTO>> next();

    boolean hasElements();
}
