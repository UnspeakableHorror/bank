package org.homenet.uhs.transaction.service.queue;

import org.homenet.uhs.transaction.web.TransactionDTO;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author uh on 2017/12/14.
 */
public class InMemoryTransactionQueueingService implements TransactionQueueingService {

    private final BlockingQueue<QueueElement<TransactionDTO>> queue = new PriorityBlockingQueue<>();

    @Override
    public boolean post(TransactionDTO transactionDTO) {
        return queue.offer(new QueueElement<>(transactionDTO));
    }

    @Override
    public synchronized Optional<QueueElement<TransactionDTO>> next() {
        return Optional.ofNullable(queue.poll());
    }

    @Override
    public boolean hasElements() {
        return !queue.isEmpty();
    }
}
